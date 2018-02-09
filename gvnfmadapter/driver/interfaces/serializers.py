# Copyright 2018 ZTE Corporation.
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

from rest_framework import serializers


class AdditionalParams(serializers.Serializer):
    sdncontroller = serializers.CharField(help_text="sdncontroller", required=False)
    NatIpRange = serializers.CharField(help_text="NatIpRange", required=False)
    m6000_mng_ip = serializers.CharField(help_text="m6000_mng_ip", required=False)
    externalPluginManageNetworkName = serializers.CharField(help_text="externalPluginManageNetworkName", required=False)
    location = serializers.CharField(help_text="location", required=False)
    externalManageNetworkName = serializers.CharField(help_text="externalManageNetworkName", required=False)
    sfc_data_network = serializers.CharField(help_text="sfc_data_network", required=False)
    externalDataNetworkName = serializers.CharField(help_text="externalDataNetworkName", required=False)
    inputs = serializers.DictField(
        help_text="inputs",
        child=serializers.CharField(help_text="but i needed to test these 2 fields somehow", allow_blank=True),
        required=False,
        allow_null=True
    )


class VnfInstReqParamsSerializer(serializers.Serializer):
    vnfDescriptorId = serializers.CharField(
        help_text="Identifier that identifies the VNFD which defines the VNF instance to be created.",
        max_length=255,
        required=True,
        allow_null=True
    )
    vnfInstanceName = serializers.CharField(
        help_text="Human-readable name of the VNF instance to be created.",
        max_length=255,
        required=True,
        allow_null=False
    )
    vnfInstanceDescription = serializers.CharField(
        help_text="Human-readable description of the VNF instance to be created.",
        max_length=255,
        required=False,
        allow_null=True
    )
    additionalParam = AdditionalParams(
        help_text="Additional input parameters for the instantiation process,"
                  " specific to the VNF being instantiated.",
        required=True
    )


class ResponseSerializer(serializers.Serializer):
    vnfInstanceId = serializers.CharField(help_text="VNF instance identifier.", required=True)
    jobId = serializers.CharField(help_text="Job ID.", required=True)


class VnfTermReqSerializer(serializers.Serializer):
    vnfInstanceId = serializers.CharField(
        help_text="VNF instance identifier.",
        max_length=255,
        required=True,
        allow_null=True
    )


class VnfInfo(serializers.Serializer):
    vnfInstanceId = serializers.CharField(help_text="VNF instance identifier.", required=True)
    vnfStatus = serializers.CharField(help_text="The instantiation state of the VNF.", required=True)
    version = serializers.CharField(help_text="Version of the VNF.", required=True)


class VnfQueryRespSerializer(serializers.Serializer):
    vnfInfo = VnfInfo(
        help_text="The information items about the selected VNF instance(s) that are returned.",
        required=True
    )
