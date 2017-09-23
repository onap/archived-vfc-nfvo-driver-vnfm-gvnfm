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

import json
import mock
from django.test import Client
from django.test import TestCase
from rest_framework import status
from driver.pub.utils import restcall


class InterfacesTest(TestCase):
    def setUp(self):
        self.client = Client()

    def tearDown(self):
        pass

    @mock.patch.object(restcall, 'call_req')
    def test_instantiate_vnf(self, mock_call_req):
        vnfm_info = {
            'userName': 'admin',
            'vendor': 'ZTE',
            'name': 'ZTE_VNFM_237_62',
            'vimId': '516cee95-e8ca-4d26-9268-38e343c2e31e',
            'url': 'http: //192.168.237.165: 2324',
            'certificateUrl': '',
            'version': 'V1.0',
            'vnfmId': 'b0797c9b-3da9-459c-b25c-3813e9d8fd70',
            'password': 'admin',
            'type': 'ztevmanagerdriver',
            'createTime': '2016-10-3111: 08: 39',
            'description': ''
        }
        job_info = {
            "vnfInstanceId": "8",
            "jobId": "NF-CREATE-8-b384535c-9f45-11e6-8749-fa163e91c2f9"
        }
        vnflcm_info = {
            "vnfInstanceId": "8",
            "vnfLcOpId": "NF-INST-8-6ffa8083-6705-49b3-ae54-cbd6265fbe7a"
        }
        r1 = [0, json.JSONEncoder().encode(vnfm_info), "200"]
        ret = [0, json.JSONEncoder().encode(job_info), '200']
        ret2 = [0, json.JSONEncoder().encode(vnflcm_info), '200']
        mock_call_req.side_effect = [r1, ret, r1, ret2]
        req_data = {
            'vnfInstanceName': 'VFW_f88c0cb7-512a-44c4-bd09-891663f19367',
            'vnfPackageId': 'd852e1be-0aac-48f1-b1a4-cd825f6cdf9a',
            'vnfDescriptorId': 'vcpe_vfw_zte_1_0',
            'additionalParam': {
                'sdncontroller': 'e4d637f1-a4ec-4c59-8b20-4e8ab34daba9',
                'NatIpRange': '192.167.0.10-192.168.0.20',
                'm6000_mng_ip': '192.168.11.11',
                'externalPluginManageNetworkName': 'plugin_net_2014',
                'location': '516cee95-e8ca-4d26-9268-38e343c2e31e',
                'externalManageNetworkName': 'mng_net_2017',
                'sfc_data_network': 'sfc_data_net_2016',
                'externalDataNetworkName': 'Flow_out_net',
                'inputs': {}
            }
        }
        response = self.client.post("/api/gvnfmdriver/v1/1/vnfs",
                                    data=json.dumps(req_data), content_type="application/json")
        self.assertEqual(status.HTTP_201_CREATED, response.status_code)
        expect_data = {
            "vnfInstanceId": "8",
            "jobId": "NF-INST-8-6ffa8083-6705-49b3-ae54-cbd6265fbe7a"
        }
        self.assertEqual(expect_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_terminate_vnf(self, mock_call_req):
        vnfm_info = {
            "vnfmId": "19ecbb3a-3242-4fa3-9926-8dfb7ddc29ee",
            "name": "g_vnfm",
            "type": "vnfm",
            "vimId": "",
            "vendor": "ZTE",
            "version": "v1.0",
            "description": "vnfm",
            "certificateUrl": "",
            "url": "http://10.74.44.11",
            "userName": "admin",
            "password": "admin",
            "createTime": "2016-07-06 15:33:18"
        }
        job_info = {"vnfInstanceId": "1", "vnfLcOpId": "1"}
        job_status_info = {
            "jobId": "1",
            "responseDescriptor": {
                "status": "",
                "progress": 100,
                "statusDescription": "",
                "errorCode": "",
                "responseId": "2",
                "responseHistoryList": [
                    {
                        "status": "",
                        "progress": "",
                        "statusDescription": "",
                        "errorCode": "",
                        "responseId": ""
                    }
                ]
            }
        }
        r1 = [0, json.JSONEncoder().encode(vnfm_info), "200"]
        r2 = [0, json.JSONEncoder().encode(job_info), "200"]
        job_ret = [0,  json.JSONEncoder().encode(job_status_info), "200"]
        mock_call_req.side_effect = [r1, r2, r1, job_ret, r1, r2]
        response = self.client.post("/api/gvnfmdriver/v1/ztevnfmid/vnfs/2/terminate")
        self.assertEqual(status.HTTP_204_NO_CONTENT, response.status_code)
        self.assertEqual(job_info, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_query_vnf(self, mock_call_req):
        vnfm_info = {
            "vnfmId": "19ecbb3a-3242-4fa3-9926-8dfb7ddc29ee",
            "name": "g_vnfm",
            "type": "vnfm",
            "vimId": "",
            "vendor": "ZTE",
            "version": "v1.0",
            "description": "vnfm",
            "certificateUrl": "",
            "url": "http://10.74.44.11",
            "userName": "admin",
            "password": "admin",
            "createTime": "2016-07-06 15:33:18"
        }
        job_info = {
            "ResponseInfo": {
                "vnfInstanceId": "88",
                "instantiationState": "INSTANTIATED",
                "vnfSoftwareVersion": "v1.2.3"
            }
        }
        r1 = [0, json.JSONEncoder().encode(vnfm_info), "200"]
        r2 = [0, json.JSONEncoder().encode(job_info), "200"]
        mock_call_req.side_effect = [r1, r2]
        response = self.client.get("/api/gvnfmdriver/v1/19ecbb3a-3242-4fa3-9926-8dfb7ddc29ee/vnfs/88")
        self.assertEqual(status.HTTP_200_OK, response.status_code)
        expect_resp_data = {
            "vnfInfo": {
                "vnfInstanceId": "88",
                "vnfStatus": "ACTIVE",
                "version": "v1.2.3"
            }
        }
        self.assertEqual(expect_resp_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_operation_status(self, mock_call_req):
        vnfm_info = {
            'userName': 'admin',
            'vendor': 'ZTE',
            'name': 'ZTE_VNFM_237_62',
            'vimId': '516cee95-e8ca-4d26-9268-38e343c2e31e',
            'url': 'http: //192.168.237.165: 2324',
            'certificateUrl': '',
            'version': 'V1.0',
            'vnfmId': 'b0797c9b-3da9-459c-b25c-3813e9d8fd70',
            'password': 'admin',
            'type': 'ztevmanagerdriver',
            'createTime': '2016-10-3111: 08: 39',
            'description': ''
        }
        expected_body = {
            "jobId": "NF-CREATE-11-ec6c2f2a-9f48-11e6-9405-fa163e91c2f9",
            "responseDescriptor": {
                "responseId": 3,
                "progress": 40,
                "status": "PROCESSING",
                "statusDescription": "OMC VMs are decommissioned in VIM",
                "errorCode": "null",
                "responseHistoryList": [
                    {
                        "status": "error",
                        "progress": 255,
                        "errorcode": "",
                        "responseid": 20,
                        "statusdescription": "'JsonParser' object has no attribute 'parser_info'"
                    }
                ]
            }
        }
        resp_body = {
            "ResponseInfo": {
                "vnfLcOpId":"NF-CREATE-11-ec6c2f2a-9f48-11e6-9405-fa163e91c2f9",
                "responseDescriptor":{
                    "responseId": 3,
                    "progress": 40,
                    "lcmOperationStatus": "PROCESSING",
                    "statusDescription": "OMC VMs are decommissioned in VIM",
                    "errorCode": "null",
                    "responseHistoryList": [
                             {"status": "error",
                              "progress": 255,
                              "errorcode": "",
                              "responseid": 20,
                              "statusdescription": "'JsonParser' object has no attribute 'parser_info'"}]
                }
            }
        }
        r1 = [0, json.JSONEncoder().encode(vnfm_info), '200']
        r2 = [0, json.JSONEncoder().encode(resp_body), '200']
        mock_call_req.side_effect = [r1, r2]
        response = self.client.get("/api/gvnfmdriver/v1/%s/jobs/%s?responseId=0"
                                   %(vnfm_info["vnfmId"], expected_body["jobId"]))
        self.assertEqual(status.HTTP_200_OK, response.status_code)
        self.assertDictEqual(expected_body, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_grantvnf(self, mock_call_req):
        vim_info = {
            "vim": {
                "accessinfo": {
                    "tenant": "admin"
                },
                "vimid": "516cee95-e8ca-4d26-9268-38e343c2e31e"
            }
        }
        req_data = {
            "vnfmid": "13232222",
            "nfvoid": "03212234",
            "vimid": "12345678",
            "exvimidlist ": "exvimid",
            "tenant": " tenant1",
            "vnfistanceid": "1234",
            "operationright": "0",
            "vmlist": [
                {
                    "vmflavor": "SMP",
                    "vmnumber": "3"
                },
                {
                    "vmflavor": "CMP",
                    "vmnumber": "3"
                }
            ]
        }
        mock_call_req.return_value = [0, json.JSONEncoder().encode(vim_info), '201']
        response = self.client.put("/api/gvnfmdriver/v1/resource/grant",
                                   data=json.dumps(req_data), content_type='application/json')
        self.assertEqual(str(status.HTTP_201_CREATED), response.status_code)
        expect_resp_data = {
            "vimid": "516cee95-e8ca-4d26-9268-38e343c2e31e",
            "tenant": "admin"
        }
        self.assertDictEqual(expect_resp_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_notify(self, mock_call_req):
        vim_info = {
            "vim": {
                "vimInfoId": "111111",
                "vimId": "12345678",
                "interfaceInfo": {
                    "vimType": "vnf",
                    "apiVersion": "v1",
                    "protocolType": "None"},
                "accessInfo": {
                    "tenant": "tenant1",
                    "username": "admin",
                    "password": "password"},
                "interfaceEndpoint": "http://127.0.0.1/api/v1"
            },
            "zone": "",
            "addResource": {
                "resourceDefinitionId": "xxxxx",
                "vimId": "12345678",
                "zoneId": "000"},
            "removeResource": "",
            "vimAssets": {
                "computeResourceFlavour": {
                    "vimId": "12345678",
                    "vduId": "sdfasdf",
                    "vimFlavourId": "12"},
                "softwareImage": {
                    "vimId": "12345678",
                    "imageName": "AAA",
                    "vimImageId": ""}},
            "additionalParam": ""
        }
        r2 = [0, json.JSONEncoder().encode(vim_info), "200"]
        mock_call_req.side_effect = [r2]
        req_data = {
            "nfvoid": "1",
            "vnfmid": "876543211",
            "vimid": "6543211",
            "timestamp": "1234567890",
            "vnfinstanceid": "1",
            "eventtype": "0",
            "vmlist":
                [
                    {
                        "vmflavor": "SMP",
                        "vmnumber": "3",
                        "vmidlist ": ["vmuuid"]},
                    {
                        "vmflavor": "CMP",
                        "vmnumber": "3",
                        "vmidlist ": ["vmuuid"]}
                ]
        }
        response = self.client.post("/api/gvnfmdriver/v1/vnfs/lifecyclechangesnotification",
                                    data=json.dumps(req_data),
                                    content_type='application/json')
        self.assertEqual(str(status.HTTP_200_OK), response.status_code)
        expect_resp_data = None
        self.assertEqual(expect_resp_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_get_vnfpkgs(self, mock_call_req):
        mock_call_req.return_value = [0, json.JSONEncoder().encode({
            "csars": [{
                "csarId": "1",
                "vnfdId": "2"
            }]
        }), '200']
        resp = self.client.get("/api/gvnfmdriver/v1/vnfpackages")
        self.assertEqual(status.HTTP_200_OK, resp.status_code)
        self.assertEqual(1, len(resp.data["csars"]))
        self.assertEqual("1", resp.data["csars"][0]["csarId"])
        self.assertEqual("2", resp.data["csars"][0]["vnfdId"])
