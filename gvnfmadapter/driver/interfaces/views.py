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

from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from driver.pub.exceptions import GvnfmDriverException
from driver.pub.utils import restcall
from driver.pub.utils.restcall import req_by_msb
from driver.interfaces.serializers import VnfInstReqParamsSerializer, ResponseSerializer
from driver.interfaces.serializers import VnfTermReqSerializer, VnfQueryRespSerializer
from driver.interfaces.serializers import VnfOperRespSerializer, VnfGrantReqSerializer, VnfGrantRespSerializer
from driver.interfaces.serializers import VnfNotifyReqSerializer, VNFLCMOpOccSerializer

logger = logging.getLogger(__name__)


class VnfInstInfo(APIView):
    @swagger_auto_schema(
        request_body=VnfInstReqParamsSerializer(),
        responses={
            status.HTTP_201_CREATED: ResponseSerializer(),
            status.HTTP_404_NOT_FOUND: "The vnfm instance id is wrong",
            status.HTTP_500_INTERNAL_SERVER_ERROR: "The url is invalid"
        }
    )
    def post(self, request, vnfmtype, vnfmid):
        logger.debug("instantiate_vnf--post::> %s" % request.data)
        logger.debug("Create vnf begin!")
        try:
            requestSerializer = VnfInstReqParamsSerializer(data=request.data)
            request_isValid = requestSerializer.is_valid()
            if not request_isValid:
                raise Exception(requestSerializer.errors)

            # requestData = requestSerializer.data
            requestData = request.data
            input_data = {
                "vnfdId": ignorcase_get(requestData, "vnfDescriptorId"),
                "vnfInstanceName": ignorcase_get(requestData, "vnfInstanceName"),
                "vnfInstanceDescription": ignorcase_get(requestData, "vnfInstanceDescription")
            }
            vnfm_id = vnfmid
            logger.debug("do_createvnf: request data=[%s],input_data=[%s],vnfm_id=[%s]",
                         request.data, input_data, vnfm_id)
            resp = do_createvnf(vnfm_id, input_data)
            logger.debug("do_createvnf: response data=[%s]", resp)
            logger.debug("Create vnf end!")

            logger.debug("Instantiate vnf start!")
            vnfInstanceId = resp["id"]
            input_data = {
                "flavourId": ignorcase_get(requestData, "flavourId"),
                "extVirtualLinks": ignorcase_get(requestData, "extVirtualLink"),
                "additionalParams": ignorcase_get(requestData, "additionalParam"),
            }
            logger.debug("do_instvnf: vnfInstanceId=[%s],request data=[%s],input_data=[%s],vnfm_id=[%s]",
                         vnfInstanceId, request.data, input_data, vnfm_id)
            resp = do_instvnf(vnfInstanceId, vnfm_id, input_data)
            logger.debug("do_instvnf: response data=[%s]", resp)
            resp_data = {
                "vnfInstanceId": vnfInstanceId,
                "jobId": ignorcase_get(resp, "jobId")
            }
            logger.debug("Instantiate vnf end!")
            return Response(data=resp_data, status=status.HTTP_201_CREATED)
        except GvnfmDriverException as e:
            logger.error('instantiate vnf failed, detail message: %s' % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class VnfTermInfo(APIView):
    @swagger_auto_schema(
        request_body=VnfTermReqSerializer(),
        responses={
            status.HTTP_201_CREATED: ResponseSerializer(),
            status.HTTP_404_NOT_FOUND: "The vnfmid and vnfInstanceId are wrong",
            status.HTTP_500_INTERNAL_SERVER_ERROR: "The url is invalid"
        }
    )
    def post(self, request, vnfmtype, vnfmid, vnfInstanceId):
        logger.debug("terminate_vnf--post::> %s" % request.data)
        logger.debug("Terminate vnf begin!")
        vnfm_id = vnfmid
        try:
            input_data = {
                "terminationType": ignorcase_get(request.data, "terminationType"),
                "gracefulTerminationTimeout": ignorcase_get(request.data, "gracefulTerminationTimeout")
            }
            logger.debug("do_terminatevnf: vnfm_id=[%s],vnfInstanceId=[%s],input_data=[%s]",
                         vnfm_id, vnfInstanceId, input_data)
            resp = do_terminatevnf(vnfm_id, vnfInstanceId, input_data)
            logger.debug("terminate_vnf: response data=[%s]", resp)

            jobId = ignorcase_get(resp, "jobId")
            gracefulTerminationTimeout = ignorcase_get(request.data, "gracefulTerminationTimeout")
            logger.debug("wait4job: vnfm_id=[%s],jobId=[%s],gracefulTerminationTimeout=[%s]",
                         vnfm_id, jobId, gracefulTerminationTimeout)
            resp = wait4job(vnfm_id, jobId, gracefulTerminationTimeout)
            logger.debug("[wait4job] response=[%s]", resp)

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


class VnfQueryInfo(APIView):
    @swagger_auto_schema(
        responses={
            status.HTTP_201_CREATED: VnfQueryRespSerializer(),
            status.HTTP_404_NOT_FOUND: "The vnfmid and vnfInstanceId are wrong",
            status.HTTP_500_INTERNAL_SERVER_ERROR: "The url is invalid"
        }
    )
    def get(self, request, vnfmtype, vnfmid, vnfInstanceId):
        logger.debug("query_vnf--post::> %s" % request.data)
        vnfm_id = vnfmid
        try:
            logger.debug("[%s] request.data=%s", fun_name(), request.data)
            resp = do_queryvnf(request, vnfm_id, vnfInstanceId)
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
            ResponseInfo = ignorcase_get(resp, "ResponseInfo")
            resp_response_data = mapping_conv(query_vnf_resp_mapping, ResponseInfo)
            resp_data = {
                "vnfInfo": resp_response_data
            }
            id = ignorcase_get(ResponseInfo, "id")
            if id:
                resp_data["vnfInfo"]["vnfInstanceId"] = id
            vnfPkgId = ignorcase_get(ResponseInfo, "vnfPkgId")
            if vnfPkgId:
                resp_data["vnfInfo"]["vnfPackageId"] = vnfPkgId
            vnfSoftwareVersion = ignorcase_get(ResponseInfo, "vnfSoftwareVersion")
            if vnfSoftwareVersion:
                resp_data["vnfInfo"]["version"] = vnfSoftwareVersion
            if ignorcase_get(ResponseInfo, "instantiationState") == "INSTANTIATED":
                resp_data["vnfInfo"]["vnfStatus"] = "ACTIVE"
            logger.debug("[%s]resp_data=%s", fun_name(), resp_data)
            return Response(data=resp_data, status=status.HTTP_200_OK)
        except GvnfmDriverException as e:
            logger.error('Query vnf failed, detail message: %s' % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class VnfOperInfo(APIView):
    @swagger_auto_schema(
        responses={
            status.HTTP_201_CREATED: VnfOperRespSerializer(),
            status.HTTP_404_NOT_FOUND: "The vnfmid, jobid and responseId are wrong",
            status.HTTP_500_INTERNAL_SERVER_ERROR: "The url is invalid"
        }
    )
    def get(self, request, vnfmtype, vnfmid, jobid):
        logger.debug("operation_status--post::> %s" % request.data)
        try:
            logger.debug("[%s] request.data=%s", fun_name(), request.data)
            vnfm_id = vnfmid
            jobId = jobid
            responseId = ignorcase_get(request.META, 'responseId')
            logger.debug("[operation_status] vnfm_id=%s", vnfm_id)
            vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
            logger.debug("[operation_status] vnfm_info=[%s]", vnfm_info)

            ret = call_vnfm("api/vnflcm/v1/vnf_lc_ops/%s?responseId=%s" % (jobId, responseId), "GET", vnfm_info)
            if ret[0] != 0:
                logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
                raise GvnfmDriverException('Failed to query vnf operation status.')
            resp_data = json.JSONDecoder().decode(ret[1])
            logger.debug("[%s]resp_data=%s", fun_name(), resp_data)
            ResponseInfo = ignorcase_get(resp_data, "ResponseInfo")
            responseDescriptor = ignorcase_get(ResponseInfo, "responseDescriptor")
            status_tmp = ignorcase_get(responseDescriptor, "lcmOperationStatus")
            del responseDescriptor["lcmOperationStatus"]
            responseDescriptor["status"] = status_tmp
            operation_data = {
                "jobId": ignorcase_get(ResponseInfo, "vnfLcOpId"),
                "responseDescriptor": responseDescriptor
            }
            return Response(data=operation_data, status=status.HTTP_200_OK)
        except GvnfmDriverException as e:
            logger.error('Query vnf failed, detail message: %s' % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class VnfGrantInfo(APIView):
    @swagger_auto_schema(
        request_body=VnfGrantReqSerializer(),  # TODO: not used
        responses={
            status.HTTP_201_CREATED: VnfGrantRespSerializer(),
            status.HTTP_404_NOT_FOUND: "The request body is wrong",
            status.HTTP_500_INTERNAL_SERVER_ERROR: "The url is invalid"
        }
    )
    def put(self, request, vnfmtype):
        try:
            logger.debug("[grantvnf] req_data = %s", request.data)
            ret = req_by_msb('api/nslcm/v2/grants', "POST", content=json.JSONEncoder().encode(request.data))
            logger.debug("ret = %s", ret)
            if ret[0] != 0:
                logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
                raise GvnfmDriverException('Failed to grant vnf.')
            resp = json.JSONDecoder().decode(ret[1])
            logger.debug("[%s]resp_data=%s", fun_name(), resp)
            return Response(data=resp, status=status.HTTP_201_CREATED)
        except GvnfmDriverException as e:
            logger.error('Grant vnf failed, detail message: %s' % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class VnfNotifyInfo(APIView):
    @swagger_auto_schema(
        request_body=VnfNotifyReqSerializer(),
        responses={
            status.HTTP_201_CREATED: "Successful Notify",
            status.HTTP_404_NOT_FOUND: "The request body is wrong",
            status.HTTP_500_INTERNAL_SERVER_ERROR: "The url is invalid"
        }
    )
    def post(self, request, vnfmtype):  # TODO: not compatable with VnfIdentifierCreationNotification and VnfIdentifierDeletionNotification
        try:
            logger.debug("[%s]req_data = %s", fun_name(), request.data)
            vnfminstid = ignorcase_get(request.data, 'vnfmInstId')
            vnfinstanceid = ignorcase_get(request.data, 'vnfInstanceId')
            request.data.pop("vnfmInstId")
            ret = req_by_msb("api/nslcm/v2/ns/%s/vnfs/%s/Notify" % (vnfminstid, vnfinstanceid), "POST",
                             json.JSONEncoder().encode(request.data))
            logger.debug("[%s]data = %s", fun_name(), ret)
            if ret[0] != 0:
                logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
                raise GvnfmDriverException('Failed to notify vnf.')
            return Response(data=None, status=status.HTTP_200_OK)
        except GvnfmDriverException as e:
            logger.error('Grant vnf failed, detail message: %s' % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class VnfPkgsInfo(APIView):
    def get(request, *args, **kwargs):
        try:
            logger.debug("Enter %s", fun_name())
            ret = req_by_msb("api/nslcm/v1/vnfpackage", "GET")
            if ret[0] != 0:
                logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
                raise GvnfmDriverException('Failed to get vnfpkgs.')
            resp = json.JSONDecoder().decode(ret[1])
            return Response(data=resp, status=status.HTTP_200_OK)
        except GvnfmDriverException as e:
            logger.error('Get vnfpkgs failed, detail message: %s' % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': 'unexpected exception'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

class QuerySingleVnfLcmOpOcc(APIView):
    @swagger_auto_schema(
        responses={
            status.HTTP_200_OK: VNFLCMOpOccSerializer(),
            status.HTTP_500_INTERNAL_SERVER_ERROR: ""
        }
    )
    def get(self, request, vnfmtype, vnfmid, lcmopoccid):
        logger.debug("[%s]LCMOpOccId = %s", fun_name(), lcmopoccid)
        try:
            vnfm_info = get_vnfminfo_from_nslcm(vnfmid)
            logger.debug("[get lcm op occ] vnfm_info=[%s]", vnfm_info)
            ret = call_vnfm("api/vnflcm/v1/vnf_lcm_op_occs/%s" % lcmopoccid, "GET", vnfm_info)
            if ret[0] != 0:
                logger.error("Status code is %s. detail is %s.", ret[2], ret[1])
                raise GvnfmDriverException("Failed to query vnf lcm op occ %s" % lcmopoccid)
            resp_data = json.JSONDecoder().decode(ret[1])
            vnf_lcm_op_occ_serializer = VNFLCMOpOccSerializer(data=resp_data)
            if vnf_lcm_op_occ_serializer.is_valid():
                logger.debug("[%s]resp_data=%s" % (fun_name(), resp_data))
                return Response(data=vnf_lcm_op_occ_serializer.data, status=status.HTTP_200_OK)
            else:
                raise GvnfmDriverException(vnf_lcm_op_occ_serializer.errors)
        except GvnfmDriverException as e:
            logger.error("Query vnflcmopocc failed, detail message: %s" % e.message)
            return Response(data={'error': e.message}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            logger.error(traceback.format_exc())
            return Response(data={'error': traceback.format_exc()}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


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
        # if keyword_map[param]:
        if isinstance(keyword_map[param], dict):
            resp_data[param] = mapping_conv(keyword_map[param], ignorcase_get(rest_return, param))
        else:
            value = ignorcase_get(rest_return, param)
            if value:
                resp_data[param] = value
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
    logger.debug("[get_vnfminfo_from_nslcm] vnfm_id=[%s]", vnfm_id)
    # ret = req_by_msb("api/aai-esr-server/v1/vnfms/%s" % vnfm_id, "GET")
    ret = req_by_msb("api/nslcm/v1/vnfms/%s" % vnfm_id, "GET")
    logger.debug("[get_vnfminfo_from_nslcm] response=%s", ret)
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException("Failed to query vnfm(%s) from nslcm." % vnfm_id)
    return json.JSONDecoder().decode(ret[1])


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
            logger.debug("Job(%s) ended normally,job_end_normal=[%s],job_timeout=[%s]",
                         job_id, job_end_normal, job_timeout)
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
    ret = call_vnfm("api/vnflcm/v1/vnf_instances/%s/terminate" % vnfInstanceId, "POST", vnfm_info, data)
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


def do_queryvnf(data, vnfm_id, vnfInstanceId):
    logger.debug("[%s] request.data=%s", fun_name(), data)
    vnfm_info = get_vnfminfo_from_nslcm(vnfm_id)
    logger.debug("[do_deletevnf] vnfm_info=[%s]", vnfm_info)
    ret = call_vnfm("api/vnflcm/v1/vnf_instances/%s" % vnfInstanceId, "GET", vnfm_info)
    if ret[0] != 0:
        logger.error("Status code is %s, detail is %s.", ret[2], ret[1])
        raise GvnfmDriverException('Failed to query vnf.')
    return json.JSONDecoder().decode(ret[1])
