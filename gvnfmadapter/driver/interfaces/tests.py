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
            'type': 'gvnfmdriver',
            'createTime': '2016-10-3T11:08:39',
            'description': ''
        }
        create_vnf_resp = {
            "id": "8",
            # "jobId": "NF-CREATE-8-b384535c-9f45-11e6-8749-fa163e91c2f9"
        }
        job_info = {
            "jobId": "NF-INST-8-6ffa8083-6705-49b3-ae54-cbd6265fbe7a"
        }
        r1 = [0, json.JSONEncoder().encode(vnfm_info), "200"]
        ret = [0, json.JSONEncoder().encode(create_vnf_resp), '200']
        ret2 = [0, json.JSONEncoder().encode(job_info), '200']
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
            "type": "gvnfmdriver",
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
        job_ret = [0, json.JSONEncoder().encode(job_status_info), "200"]
        r3 = [0, json.JSONEncoder().encode(None), "200"]
        mock_call_req.side_effect = [r1, r2, r1, job_ret, r1, r3]
        response = self.client.post("/api/gvnfmdriver/v1/ztevnfmid/vnfs/2/terminate")
        self.assertEqual(status.HTTP_204_NO_CONTENT, response.status_code)
        self.assertEqual(None, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_query_vnf(self, mock_call_req):
        vnfm_info = {
            "vnfmId": "19ecbb3a-3242-4fa3-9926-8dfb7ddc29ee",
            "name": "g_vnfm",
            "type": "gvnfmdriver",
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
                "id": "88",
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
            'type': 'gvnfmdriver',
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
                "vnfLcOpId": "NF-CREATE-11-ec6c2f2a-9f48-11e6-9405-fa163e91c2f9",
                "responseDescriptor": {
                    "responseId": 3,
                    "progress": 40,
                    "lcmOperationStatus": "PROCESSING",
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
        }
        r1 = [0, json.JSONEncoder().encode(vnfm_info), '200']
        r2 = [0, json.JSONEncoder().encode(resp_body), '200']
        mock_call_req.side_effect = [r1, r2]
        response = self.client.get("/api/gvnfmdriver/v1/%s/jobs/%s?responseId=0"
                                   % (vnfm_info["vnfmId"], expected_body["jobId"]))
        self.assertEqual(status.HTTP_200_OK, response.status_code)
        self.assertDictEqual(expected_body, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_grantvnf(self, mock_call_req):
        data = {
            "vnfInstanceId": "1",
            "vnfLcmOpOccId": "2",
            "vnfdId": "3",
            "flavourId": "4",
            "operation": "INSTANTIATE",
            "isAutomaticInvocation": True,
            "instantiationLevelId": "5",
            "addResources": [
                {
                    "id": "1",
                    "type": "COMPUTE",
                    "vduId": "2",
                    "resourceTemplateId": "3",
                    "resourceTemplate": {
                        "vimConnectionId": "4",
                        "resourceProviderId": "5",
                        "resourceId": "6",
                        "vimLevelResourceType": "7"
                    }
                }
            ],
            "placementConstraints": [
                {
                    "affinityOrAntiAffinity": "AFFINITY",
                    "scope": "NFVI_POP",
                    "resource": [
                        {
                            "idType": "RES_MGMT",
                            "resourceId": "1",
                            "vimConnectionId": "2",
                            "resourceProviderId": "3"
                        }
                    ]
                }
            ],
            "vimConstraints": [
                {
                    "sameResourceGroup": True,
                    "resource": [
                        {
                            "idType": "RES_MGMT",
                            "resourceId": "1",
                            "vimConnectionId": "2",
                            "resourceProviderId": "3"
                        }
                    ]
                }
            ],
            "additionalParams": {},
            "_links": {
                "vnfLcmOpOcc": {
                    "href": "1"
                },
                "vnfInstance": {
                    "href": "2"
                }
            }
        }
        grant = {
            'id': 'Identifier of the garnt',
            'vnfInstanceId': 'Identifier of the related VNF instance',
            'vnfLcmOpOccId': 'Identifier of the related VNF LcmOpOcc',
            # NOT REQUIERD #
            # 'vimConnections': [],
            # 'zones': [],
            # 'zoneGroups': [],
            # 'computeReservationId': None,
            # 'networkReservationId': None,
            # 'storageReservationId': None,
            # 'addResources': None,
            # 'tempResources': None,
            # 'removeResource': None,
            # 'updateResource': None,
            # 'vimAssets': None,
            # 'extVirtualLinks': None,
            # 'extManagedVirtualLinks': None,
            # 'additionalParams': None,
            '_links': {
                'self': {'href': 'URI of this resource'},
                'vnfLcmOpOcc': {'href': 'Related VNF lifecycle management operation occurrence'},
                'vnfInstance': {'href': 'Related VNF instance'}
            }
        }

        mock_call_req.return_value = [0, json.JSONEncoder().encode(grant), '201']
        response = self.client.put("/api/gvnfmdriver/v1/resource/grant",
                                   data=json.dumps(data), content_type='application/json')
        self.assertEqual(status.HTTP_201_CREATED, response.status_code)
        expect_resp_data = {
            'id': 'Identifier of the garnt',
            'vnfInstanceId': 'Identifier of the related VNF instance',
            'vnfLcmOpOccId': 'Identifier of the related VNF LcmOpOcc',
            # NOT REQUIERD #
            # 'vimConnections': [],
            # 'zones': [],
            # 'zoneGroups': [],
            # 'computeReservationId': None,
            # 'networkReservationId': None,
            # 'storageReservationId': None,
            # 'addResources': None,
            # 'tempResources': None,
            # 'removeResource': None,
            # 'updateResource': None,
            # 'vimAssets': None,
            # 'extVirtualLinks': None,
            # 'extManagedVirtualLinks': None,
            # 'additionalParams': None,
            '_links': {
                'self': {'href': 'URI of this resource'},
                'vnfLcmOpOcc': {'href': 'Related VNF lifecycle management operation occurrence'},
                'vnfInstance': {'href': 'Related VNF instance'}
            }
        }
        self.assertDictEqual(expect_resp_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_grantvnf_failed(self, mock_call_req):
        data = {
            "vnfInstanceId": "1",
            "vnfLcmOpOccId": "2",
            "vnfdId": "3",
            "flavourId": "4",
            "operation": "INSTANTIATE",
            "isAutomaticInvocation": True,
            "instantiationLevelId": "5",
            "addResources": [
                {
                    "id": "1",
                    "type": "COMPUTE",
                    "vduId": "2",
                    "resourceTemplateId": "3",
                    "resourceTemplate": {
                        "vimConnectionId": "4",
                        "resourceProviderId": "5",
                        "resourceId": "6",
                        "vimLevelResourceType": "7"
                    }
                }
            ],
            "placementConstraints": [
                {
                    "affinityOrAntiAffinity": "AFFINITY",
                    "scope": "NFVI_POP",
                    "resource": [
                        {
                            "idType": "RES_MGMT",
                            "resourceId": "1",
                            "vimConnectionId": "2",
                            "resourceProviderId": "3"
                        }
                    ]
                }
            ],
            "vimConstraints": [
                {
                    "sameResourceGroup": True,
                    "resource": [
                        {
                            "idType": "RES_MGMT",
                            "resourceId": "1",
                            "vimConnectionId": "2",
                            "resourceProviderId": "3"
                        }
                    ]
                }
            ],
            "additionalParams": {},
            "_links": {
                "vnfLcmOpOcc": {
                    "href": "1"
                },
                "vnfInstance": {
                    "href": "2"
                }
            }
        }
        mock_call_req.return_value = [1, json.JSONEncoder().encode(""), '201']
        response = self.client.put("/api/gvnfmdriver/v1/resource/grant",
                                   data=json.dumps(data), content_type='application/json')
        self.assertEqual(status.HTTP_500_INTERNAL_SERVER_ERROR, response.status_code)

    @mock.patch.object(restcall, 'call_req')
    def test_notify(self, mock_call_req):
        vim_info = {
            "vim": {
                "vimInfoId": "111111",
                "vimId": "12345678",
                "interfaceInfo": {
                    "vimType": "vnf",
                    "apiVersion": "v1",
                    "protocolType": "None"
                },
                "accessInfo": {
                    "tenant": "tenant1",
                    "username": "admin",
                    "password": "password"
                },
                "interfaceEndpoint": "http://127.0.0.1/api/v1"
            },
            "zone": "",
            "addResource": {
                "resourceDefinitionId": "xxxxx",
                "vimId": "12345678",
                "zoneId": "000"
            },
            "removeResource": "",
            "vimAssets": {
                "computeResourceFlavour": {
                    "vimId": "12345678",
                    "vduId": "sdfasdf",
                    "vimFlavourId": "12"
                },
                "softwareImage": {
                    "vimId": "12345678",
                    "imageName": "AAA",
                    "vimImageId": ""
                }
            },
            "additionalParam": ""
        }
        r2 = [0, json.JSONEncoder().encode(vim_info), "200"]
        mock_call_req.side_effect = [r2]
        req_data = {
            "vnfmInstId": "876543211",
            "notificationType": "string",
            "subscriptionId": "string",
            "timeStamp": "1234567890",
            "notificationStatus": "START",
            "operationState": "STARTING",
            "vnfInstanceId": "string",
            "operation": "INSTANTIATE",
            "isAutomaticInvocation": True,
            "vnfLcmOpOccId": "string",
            "affectedVnfcs": [{
                "id": "string",
                "vduId": "string",
                "changeType": "ADDED",
                "computeResource": {
                    "vimConnectionId": "string",
                    "resourceProviderId": "string",
                    "resourceId": "string",
                    "vimLevelResourceType": "string"
                },
                "metadata": {},
                "affectedVnfcCpIds": [],
                "addedStorageResourceIds": [],
                "removedStorageResourceIds": [],
            }],
            "affectedVirtualLinks": [{
                "id": "string",
                "virtualLinkDescId": "string",
                "changeType": "ADDED",
                "networkResource": {
                    "vimConnectionId": "string",
                    "resourceProviderId": "string",
                    "resourceId": "string",
                    "vimLevelResourceType": "network",
                }
            }],
            "affectedVirtualStorages": [{
                "id": "string",
                "virtualStorageDescId": "string",
                "changeType": "ADDED",
                "storageResource": {
                    "vimConnectionId": "string",
                    "resourceProviderId": "string",
                    "resourceId": "string",
                    "vimLevelResourceType": "network",
                },
                "metadata": {}
            }],
            "changedInfo": {
                "vnfInstanceName": "string",
                "vnfInstanceDescription": "string",
                "vnfConfigurableProperties": {},
                "metadata": {},
                "extensions": {},
                "vimConnectionInfo": [{
                    "id": "string",
                    "vimId": "string",
                    "vimType": "string",
                    "interfaceInfo": {},
                    "accessInfo": {},
                    "extra": {}
                }],
                "vnfPkgId": "string",
                "vnfdId": "string",
                "vnfProvider": "string",
                "vnfProductName": "string",
                "vnfSoftwareVersion": "string",
                "vnfdVersion": "string"
            },
            "changedExtConnectivity": [{
                "id": "string",
                "resourceHandle": {
                    "vimConnectionId": "string",
                    "resourceProviderId": "string",
                    "resourceId": "string",
                    "vimLevelResourceType": "string"
                },
                "extLinkPorts": [{
                    "id": "string",
                    "resourceHandle": {
                        "vimConnectionId": "string",
                        "resourceProviderId": "string",
                        "resourceId": "string",
                        "vimLevelResourceType": "string"
                    },
                    "cpInstanceId": "string"
                }]
            }]
        }
        response = self.client.post("/api/gvnfmdriver/v1/vnfs/lifecyclechangesnotification",
                                    data=json.dumps(req_data),
                                    content_type='application/json')
        self.assertEqual(status.HTTP_200_OK, response.status_code)
        expect_resp_data = None
        self.assertEqual(expect_resp_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_get_vnfpkgs(self, mock_call_req):
        vnfpkgs_info = {
            "csars": [{
                "csarId": "1",
                "vnfdId": "2"
            }]
        }
        mock_call_req.return_value = [0, json.JSONEncoder().encode(vnfpkgs_info), '200']
        resp = self.client.get("/api/gvnfmdriver/v1/vnfpackages")
        self.assertEqual(status.HTTP_200_OK, resp.status_code)
        self.assertEqual(1, len(resp.data["csars"]))
        self.assertEqual("1", resp.data["csars"][0]["csarId"])
        self.assertEqual("2", resp.data["csars"][0]["vnfdId"])

    @mock.patch.object(restcall, 'call_req')
    def test_get_vnfpkgs_failed(self, mock_call_req):
        mock_call_req.return_value = [1, json.JSONEncoder().encode(""), '200']
        resp = self.client.get("/api/gvnfmdriver/v1/vnfpackages")
        self.assertEqual(status.HTTP_500_INTERNAL_SERVER_ERROR, resp.status_code)

    @mock.patch.object(restcall, 'call_req')
    def test_get_vnflcmopocc_with_id(self, mock_call_req):
        vnfLcmOpOccId = "99442b18-a5c7-11e8-998c-bf1755941f16"
        vnfm_info = {
            "vnfmId": "19ecbb3a-3242-4fa3-9926-8dfb7ddc29ee",
            "name": "g_vnfm",
            "type": "gvnfmdriver",
            "vimId": "",
            "vendor": "ZTE",
            "version": "v1.0",
            "description": "vnfm",
            "certificateUrl": "",
            "url": "http://10.74.44.11",
            "userName": "admin",
            "password": "admin",
            "createTime": "2016-07-06T15:33:18"
        }
        dummy_single_vnf_lcm_op = {
            "id": vnfLcmOpOccId,
            "operationState": "STARTING",
            "stateEnteredTime": "2018-07-09",
            "startTime": "2018-07-09",
            "vnfInstanceId": "cd552c9c-ab6f-11e8-b354-236c32aa91a1",
            "grantId": None,
            "operation": "SCALE",
            "isAutomaticInvocation": False,
            "operationParams": {},
            "isCancelPending": False,
            "cancelMode": None,
            "error": None,
            "resourceChanges": None,
            "changedInfo": None,
            "changedExtConnectivity": None,
            "_links": {
                "self": {
                    "href": "dem1o"
                },
                "vnfInstance": "demo"
            }
        }
        mock_call_req.return_value = [0, json.JSONEncoder().encode(dummy_single_vnf_lcm_op), status.HTTP_200_OK]
        resp = self.client.get("/api/gvnfmdriver/v1/%s/vnf_lcm_op_occs/%s" % (vnfm_info['vnfmId'], vnfLcmOpOccId))
        self.assertEqual(dummy_single_vnf_lcm_op, resp.data)
        self.assertEqual(status.HTTP_200_OK, resp.status_code)

    @mock.patch.object(restcall, 'call_req')
    def test_get_vnflcmopocc_failed(self, mock_call_req):
        vnfLcmOpOccId = "99442b18-a5c7-11e8-998c-bf1755941f16"
        vnfm_info = {
            "vnfmId": "19ecbb3a-3242-4fa3-9926-8dfb7ddc29ee",
            "name": "g_vnfm",
            "type": "gvnfmdriver",
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
        mock_call_req.return_value = [1, json.JSONEncoder().encode({}), status.HTTP_500_INTERNAL_SERVER_ERROR]
        resp = self.client.get("/api/gvnfmdriver/v1/%s/vnf_lcm_op_occs/%s" % (vnfm_info['vnfmId'], vnfLcmOpOccId))
        self.assertEqual(status.HTTP_500_INTERNAL_SERVER_ERROR, resp.status_code)

    @mock.patch.object(restcall, 'call_req')
    def test_subscribe_successfully(self, mock_call_req):
        lccn_subscription_request_data = {
            "filter": {
                "notificationTypes": ["VnfLcmOperationOccurrenceNotification"],
                "operationTypes": ["INSTANTIATE"],
                "operationStates": ["STARTING"],
            },
            "callbackUri": "http://aurl.com",
            "authentication": {
                "authType": ["BASIC"],
                "paramsBasic": {
                    "username": "username",
                    "password": "password"
                }
            }
        }
        lccn_subscription_data = {
            "id": "cd552c9c-ab6f-11e8-b354-236c32aa91a1",
            "callbackUri": "http://aurl.com",
            "filter": {
                "notificationTypes": ["VnfLcmOperationOccurrenceNotification"],
                "operationTypes": ["INSTANTIATE"],
                "operationStates": ["STARTING"]
            },
            "_links": {
                "self": {"href": "URI of this resource."}
            },
        }
        mock_call_req.return_value = [0, json.JSONEncoder().encode(lccn_subscription_data), status.HTTP_201_CREATED]
        response = self.client.post("/api/gvnfmdriver/v1/subscriptions", json.dumps(lccn_subscription_request_data),
                                    content_type='application/json')
        self.assertEqual(status.HTTP_201_CREATED, response.status_code)
        self.assertEqual(lccn_subscription_data, response.data)

    @mock.patch.object(restcall, 'call_req')
    def test_subscribe_failed(self, mock_call_req):
        lccn_subscription_request_data = {
            "filter": {
                "notificationTypes": ["VnfLcmOperationOccurrenceNotification"],
                "operationTypes": ["INSTANTIATE"],
                "operationStates": ["STARTING"],
            },
            "callbackUri": "http://aurl.com",
            "authentication": {
                "authType": ["BASIC"],
                "paramsBasic": {
                    "username": "username",
                    "password": "password"
                }
            }
        }
        mock_call_req.return_value = [1, None, status.HTTP_303_SEE_OTHER]
        response = self.client.post("/api/gvnfmdriver/v1/subscriptions", json.dumps(lccn_subscription_request_data),
                                    content_type='application/json')
        self.assertEqual(status.HTTP_500_INTERNAL_SERVER_ERROR, response.status_code)
