# Copyright 2017 ZTE Corporation.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import inspect
import json
import logging
import time
import traceback

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from driver.pub.exceptions import GvnfmDriverException
from driver.pub.utils import restcall
from driver.pub.utils.restcall import req_by_msb

logger = logging.getLogger(__name__)


@api_view(http_method_names=['POST'])
def instantiate_vnf(request, *args, **kwargs):
    try:
        logger.debug("instantiate_vnf--post::> %s" % request.data)
        logger.info("Create vnf begin!")
        input_data = {}
        input_data["vnfdId"] = ignorcase_get(request.data, "vnfDescriptorId")
        input_data["vnfInstanceName"] = ignorcase_get(request.data, "vnfInstanceName")
        input_data["vnfInstanceDescription"] = ignorcase_get(request.data, "vnfInstanceDescription")
        vnfm_id = ignorcase_get(kwargs, "vnfmid")
        logger.debug("do_createvnf: request data=[%s],input_data=[%s],vnfm_id=[%s]", request.data, input_data, vnfm_id)
        resp = do_createvnf(vnfm_id, input_data)
        logger.debug("do_createvnf: response data=[%s]", resp)
        logger.debug("Create vnf end!")

        logger.debug("Instantiate vnf start!")
        vnfInstanceId = resp["vnfInstanceId"]
        input_data = {}
        input_data["flavourId"] = ignorcase_get(request.data, "flavourId")
        input_data["extVirtualLinks"] = ignorcase_get(request.data, "extVirtualLink")
        input_data["additionalParams"] = ignorcase_get(request.data, "additionalParam")
        logger.debug("do_instvnf: vnfInstanceId=[%s],request data=[%s],input_data=[%s],vnfm_id=[%s]",
                     vnfInstanceId, request.data, input_data, vnfm_id)
        resp = do_instvnf(vnfInstanceId, vnfm_id, input_data)
        logger.debug("do_instvnf: response data=[%s]", resp)
        resp_data = {
            "vnfInstanceId": vnfInstanceId,
            "jobId": ignorcase_get(resp, "vnfLcOpId")
        }
        logger.debug("Instantiate vnf end!")
        return Response(data=resp_data, status=status.HTTP_201_CREATED)
    except GvnfmDriverException as e:
        logger.error('instantiate vnf failed, detail message: %s' % e.message)
        return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    except:
        logger.error(traceback.format_exc())
        return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


@api_view(http_method_names=['POST'])
def terminate_vnf(request, *args, **kwargs):
    logger.debug("terminate_vnf--post::> %s" % request.data)
    logger.info("Terminate vnf begin!")
    vnfm_id = ignorcase_get(kwargs, "vnfmid")
    vnfInstanceId = ignorcase_get(kwargs, "vnfInstanceId")
    try:
        input_data = {}
        input_data["terminationType"] = ignorcase_get(request.data, "terminationType")
        input_data["gracefulTerminationTimeout"] = ignorcase_get(request.data, "gracefulTerminationTimeout")
        logger.debug("do_terminatevnf: vnfm_id=[%s],vnfInstanceId=[%s],input_data=[%s]",
                     vnfm_id, vnfInstanceId, input_data)
        resp = do_terminatevnf(vnfm_id, vnfInstanceId, input_data)
        logger.debug("terminate_vnf: response data=[%s]", resp)

        jobId = ignorcase_get(resp, "vnfLcOpId")
        gracefulTerminationTimeout = ignorcase_get(request.data, "gracefulTerminationTimeout")
        logger.debug("wait4job: vnfm_id=[%s],jobId=[%s],gracefulTerminationTimeout=[%s]",
                     vnfm_id, jobId, gracefulTerminationTimeout)
        resp = wait4job(vnfm_id, jobId, gracefulTerminationTimeout)
        logger.info("[wait4job] response=[%s]", resp)

        logger.debug("Delete vnf start!")
        logger.debug("do_deletevnf: vnfm_id=[%s],vnfInstanceId=[%s],request data=[%s]",
                     vnfm_id, vnfInstanceId, request.data)
        resp = do_deletevnf(vnfm_id, vnfInstanceId, request.data)
        logger.debug("do_deletevnf: response data=[%s]", resp)
        logger.debug("Delete vnf end!")

        return Response(data=resp, status=status.HTTP_204_NO_CONTENT)
    except GvnfmDriverException as e:
        logger.error('Terminate vnf failed, detail message: %s' % e.message)
        return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    except:
        logger.error(traceback.format_exc())
        return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


@api_view(http_method_names=['GET'])
def query_vnf(request, *args, **kwargs):
    vnfm_id = ignorcase_get(kwargs, "vnfmid")
    vnfInstanceId = ignorcase_get(kwargs, "vnfInstanceId")
    try:
        logger.debug("[%s] request.data=%s", fun_name(), request.data)
        ret, resp = do_queryvnf(request, vnfm_id, vnfInstanceId)
        if ret != 0:
            return resp
        query_vnf_resp_mapping = {
            "vnfInstanceId": "",
            "vnfInstanceName": "",
            "vnfInstanceDescription": "",
            "vnfdId": "",
            "vnfPackageId": "",
            "version": "",
            "vnfProvider": "",
            "vnfType": "",
            "vnfStatus": ""
        }
        resp_response_data = mapping_conv(query_vnf_resp_mapping, ignorcase_get(resp, "ResponseInfo"))
        resp_data = {
            "vnfInfo":resp_response_data
        }
        resp_data["vnfInfo"]["version"] = ignorcase_get(ignorcase_get(resp, "ResponseInfo"), "vnfSoftwareVersion")
        if ignorcase_get(ignorcase_get(resp, "ResponseInfo"), "instantiationState"):
            if ignorcase_get(ignorcase_get(resp, "ResponseInfo"), "instantiationState") == "INSTANTIATED":
                resp_data["vnfInfo"]["vnfStatus"] = "ACTIVE"
        if ignorcase_get(ignorcase_get(resp, "ResponseInfo"), "vnfInstanceId"):
            resp_data["vnfInfo"]["vnfInstanceId"] = ignorcase_get(ignorcase_get(resp, "ResponseInfo"), "vnfInstanceId")
        logger.debug("[%s]resp_data=%s", fun_name(), resp_data)
    except Exception as e:
        logger.error("Error occurred when querying VNF information.")
        raise e
    return Response(data=resp_data, status=status.HTTP_200_OK)


@api_view(http_method_names=['GET'])
def operation_status(request, *args, **kwargs):
    data = {}
    try:
        logger.debug("[%s] request.data=%s", fun_name(), request.data)
        vnfm_id = ignorcase_get(kwargs, "vnfmid")
        jobId = ignorcase_get(kwargs, "jobId")
        responseId = ignorcase_get(kwargs, "responseId")
        ret, vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
        if ret != 0:
            return Response(data={'error': ret[1]}, status=ret[2])
        logger.debug("[%s] vnfm_info=%s", fun_name(), vnfm_info)
        ret = call_vnfm("api/vnflcm/v1/vnf_lc_ops/%s?responseId=%s" % (jobId, responseId), "GET", vnfm_info)
        if ret[0] != 0:
            return Response(data={'error': ret[1]}, status=ret[2])
        resp_data = json.JSONDecoder().decode(ret[1])
        logger.info("[%s]resp_data=%s", fun_name(), resp_data)
        ResponseInfo = ignorcase_get(resp_data, "ResponseInfo")
        operation_data = {}
        operation_data["jobId"] = ignorcase_get(ResponseInfo, "vnfLcOpId")
        operation_data["responseDescriptor"] = {}
        operation_data["responseDescriptor"]["status"] = ignorcase_get(ignorcase_get(ResponseInfo, "responseDescriptor"),"lcmOperationStatus")
        operation_data["responseDescriptor"]["progress"] = ignorcase_get(ignorcase_get(ResponseInfo, "responseDescriptor"),"progress")
        operation_data["responseDescriptor"]["statusDescription"] = ignorcase_get(ignorcase_get(ResponseInfo, "responseDescriptor"),"statusDescription")
        operation_data["responseDescriptor"]["errorCode"] = ignorcase_get(ignorcase_get(ResponseInfo, "responseDescriptor"),"errorCode")
        operation_data["responseDescriptor"]["responseId"] = ignorcase_get(ignorcase_get(ResponseInfo, "responseDescriptor"),"responseId")
        operation_data["responseDescriptor"]["responseHistoryList"] = ignorcase_get(ignorcase_get(ResponseInfo, "responseDescriptor"),"responseHistoryList")
    except Exception as e:
        logger.error("Error occurred when getting operation status information.")
        raise e
    return Response(data=operation_data, status=status.HTTP_200_OK)


@api_view(http_method_names=['PUT'])
def grantvnf(request, *args, **kwargs):
    logger.info("=====grantvnf=====")
    try:
        resp_data = {}
        logger.info("req_data = %s", request.data)
        ret = req_by_msb('api/nslcm/v1/grantvnf', "POST", content=json.JSONEncoder().encode(request.data))
        logger.info("ret = %s", ret)
        if ret[0] != 0:
            return Response(data={'error': ret[1]}, status=ret[2])
        resp = json.JSONDecoder().decode(ret[1])
        resp_data['vimid'] = ignorcase_get(resp['vim'], 'vimid')
        resp_data['tenant'] = ignorcase_get(ignorcase_get(resp['vim'], 'accessinfo'), 'tenant')
        logger.info("[%s]resp_data=%s", fun_name(), resp_data)
    except Exception as e:
        logger.error("Error occurred in Grant VNF.")
        raise e
    return Response(data=resp_data, status=ret[2])


@api_view(http_method_names=['POST'])
def notify(request, *args, **kwargs):
    try:
        logger.info("[%s]req_data = %s", fun_name(), request.data)
        vnfinstanceid = ignorcase_get(request.data, 'vnfinstanceid')
        ret = req_by_msb("api/nslcm/v1/vnfs/%s/Notify" % vnfinstanceid, "POST", json.JSONEncoder().encode(request.data))
        logger.info("[%s]data = %s", fun_name(), ret)
        if ret[0] != 0:
            return Response(data={'error': ret[1]}, status=ret[2])
    except Exception as e:
        logger.error("Error occurred in LCM notification.")
        raise e
    return Response(data=None, status=ret[2])


@api_view(http_method_names=['GET'])
def get_vnfpkgs(request, *args, **kwargs):
    logger.info("Enter %s", fun_name())
    ret = req_by_msb("api/nslcm/v1/vnfpackage", "GET")
    if ret[0] != 0:
        return Response(data={'error': ret[1]}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    resp = json.JSONDecoder().decode(ret[1])
    return Response(data=resp, status=status.HTTP_200_OK)


def call_vnfm(resource, method, vnfm_info, data=""):
    ret = restcall.call_req(
        base_url=ignorcase_get(vnfm_info, "url"),
        user=ignorcase_get(vnfm_info, "userName"),
        passwd=ignorcase_get(vnfm_info, "password"),
        auth_type=restcall.rest_no_auth,
        resource=resource,
        method=method,
        content=json.JSONEncoder().encode(data))
    return ret


def mapping_conv(keyword_map, rest_return):
    resp_data = {}
    for param in keyword_map:
        if keyword_map[param]:
            if isinstance(keyword_map[param], dict):
                resp_data[param] = mapping_conv(keyword_map[param], ignorcase_get(rest_return, param))
            else:
                resp_data[param] = ignorcase_get(rest_return, param)
    return resp_data


def fun_name():
    return "=========%s=========" % inspect.stack()[1][3]


def ignorcase_get(args, key):
    if not key:
        return ""
    if not args:
        return ""
    if key in args:
        return args[key]
    for old_key in args:
        if old_key.upper() == key.upper():
            return args[old_key]
    return ""


def get_vnfminfo_from_nslcm(vnfm_id):
    ret = req_by_msb("api/aai-esr-server/v1/vnfms/%s" % vnfm_id, "GET")
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException("Failed to query vnfm(%s) from nslcm." % vnfm_id)
    return json.JSONDecoder().decode(ret[1])


# def wait4job(vnfm_id, jobId, gracefulTerminationTimeout):
#     begin_time = time.time()
#     try:
#         logger.debug("[wait4job] vnfm_id=[%s],jobId=[%s],gracefulTerminationTimeout=[%s]",
#                      vnfm_id, jobId, gracefulTerminationTimeout)
#         vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
#         logger.debug("[do_terminatevnf] vnfm_info=[%s]", vnfm_info)
#
#         ret, vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
#         if ret != 0:
#             return 255, Response(data={"error":"Fail to get VNFM!"}, status=status.HTTP_412_PRECONDITION_FAILED)
#
#         responseId = None
#         while ret == 0:
#             cur_time = time.time()
#             if gracefulTerminationTimeout and (cur_time - begin_time > gracefulTerminationTimeout):
#                 return 255, Response(data={"error":"Fail to terminate VNF!"}, status=status.HTTP_408_REQUEST_TIMEOUT)
#             ret = call_vnfm("api/vnflcm/v1/vnf_lc_ops/%s?responseId=%s" % (jobId, responseId), "GET", vnfm_info)
#             if ret[0] != 0:
#                 return 255, Response(data={"error":"Fail to get job status!"}, status=status.HTTP_412_PRECONDITION_FAILED)
#             if json.JSONDecoder().decode(ret[2]) != 200:
#                 return 255, Response(data={"error":"Fail to get job status!"}, status=status.HTTP_412_PRECONDITION_FAILED)
#             job_info = json.JSONDecoder().decode(ret[1])
#             responseId = ignorcase_get(ignorcase_get(job_info, "VnfLcOpResponseDescriptor"), "responseId")
#             progress = ignorcase_get(ignorcase_get(job_info, "VnfLcOpResponseDescriptor"), "progress")
#             if progress == "100":
#                 return 0, Response(data={"success":"success"}, status=status.HTTP_204_NO_CONTENT)
#     except Exception as e:
#         logger.error("Error occurred when do_createvnf")
#         return 255, Response(data={"error":"Exception caught! Fail to get job status!"}, status=status.HTTP_412_PRECONDITION_FAILED)

def wait4job(vnfm_id, job_id, gracefulTerminationTimeout=1200, retry_count=60, interval_second=3):
    logger.debug("[wait4job] vnfm_id=[%s],jobId=[%s],gracefulTerminationTimeout=[%s]",
                 vnfm_id, job_id, gracefulTerminationTimeout)
    count = 0
    response_id, new_response_id = 0, 0
    job_end_normal, job_timeout = False, True
    vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
    logger.debug("[do_terminatevnf] vnfm_info=[%s]", vnfm_info)

    while count < retry_count:
        count = count + 1
        time.sleep(interval_second)
        ret = call_vnfm("api/vnflcm/v1/vnf_lc_ops/%s?responseId=%s" % (job_id, response_id), "GET", vnfm_info)
        if ret[0] != 0:
            logger.error("Failed to query job: %s:%s", ret[2], ret[1])
            continue
        job_result = json.JSONDecoder().decode(ret[1])
        if "responseDescriptor" not in job_result:
            logger.error("Job(%s) does not exist.", job_id)
            continue
        progress = job_result["responseDescriptor"]["progress"]
        new_response_id = job_result["responseDescriptor"]["responseId"]
        job_desc = job_result["responseDescriptor"]["statusDescription"]
        if new_response_id != response_id:
            logger.debug("%s:%s:%s", progress, new_response_id, job_desc)
            response_id = new_response_id
            count = 0
        if progress == 255:
            job_timeout = False
            logger.error("Job(%s) failed: %s", job_id, job_desc)
            break
        elif progress == 100:
            job_end_normal, job_timeout = True, False
            logger.info("Job(%s) ended normally", job_id)
            return {"success": "success"}
    if job_timeout:
        logger.error("Job(%s) timeout", job_id)
    raise GvnfmDriverException("Fail to get job status!")


def do_createvnf(vnfm_id, data):
    logger.debug("[%s] request.data=%s", fun_name(), data)
    vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
    logger.debug("[do_createvnf] vnfm_info=[%s]", vnfm_info)
    ret = call_vnfm("api/vnflcm/v1/vnf_instances", "POST", vnfm_info, data)
    logger.debug("[%s] call_req ret=%s", fun_name(), ret)
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException('Failed to create vnf.')
    return json.JSONDecoder().decode(ret[1])


def do_instvnf(vnfInstanceId, vnfm_id, data):
    logger.debug("[%s] request.data=%s", fun_name(), data)
    vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
    logger.debug("[do_instvnf] vnfm_info=[%s]", vnfm_info)
    ret = call_vnfm("api/vnflcm/v1/vnf_instances/%s/instantiate" % vnfInstanceId, "POST", vnfm_info, data)
    logger.debug("[%s] call_req ret=%s", fun_name(), ret)
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException('Failed to inst vnf.')
    return json.JSONDecoder().decode(ret[1])


def do_terminatevnf(vnfm_id, vnfInstanceId, data):
    logger.debug("[%s] request.data=%s", fun_name(), data)
    vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
    logger.debug("[do_terminatevnf] vnfm_info=[%s]", vnfm_info)
    ret = call_vnfm("api/vnflcm/v1/vnf_instances/%s/terminate"% vnfInstanceId,"POST", vnfm_info, data)
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException('Failed to terminate vnf.')
    return json.JSONDecoder().decode(ret[1])


def do_deletevnf(vnfm_id, vnfInstanceId, data):
    logger.debug("[%s] request.data=%s", fun_name(), data)
    vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
    logger.debug("[do_deletevnf] vnfm_info=[%s]", vnfm_info)
    ret = call_vnfm("api/vnflcm/v1/vnf_instances/%s" % vnfInstanceId, "DELETE", vnfm_info)
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException('Failed to delete vnf.')
    return json.JSONDecoder().decode(ret[1])


def do_queryvnf(request, vnfm_id, vnfInstanceId):
    logger.debug("[%s] request.data=%s", fun_name(), request.data)
    try:
        ret, vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
        if ret != 0:
            return ret, vnfm_info
        ret = call_vnfm("api/vnflcm/v1/vnf_instances/%s" % vnfInstanceId, "GET", vnfm_info)
        if ret[0] != 0:
            return 255, Response(data={'error': ret[1]}, status=ret[2])
        resp_data = json.JSONDecoder().decode(ret[1])
        logger.debug("[%s]resp_data=%s", fun_name(), resp_data)
    except Exception as e:
        logger.error("Error occurred when do_query vnf")
        raise e
    return 0, resp_data
