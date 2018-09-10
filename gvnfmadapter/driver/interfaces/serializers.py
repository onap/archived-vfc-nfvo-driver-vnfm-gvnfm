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


class ResourceHandleSerializer(serializers.Serializer):
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the VIM connection to manage the resource.",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifier of the entity responsible for the management of the resource.",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    resourceId = serializers.CharField(
        help_text="Identifier of the resource in the scope of the VIM or the resource provider.",
        required=True
    )
    vimLevelResourceType = serializers.CharField(
        help_text="Type of the resource in the scope of the VIM or the resource provider.",
        required=False,
        allow_null=True,
        allow_blank=True
    )


class ResourceDefinitionSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="Identifier of this ResourceDefinition, unique at least within the scope of the GrantRequest.",
        required=True
    )
    type = serializers.ChoiceField(
        help_text="Type of the resource definition referenced.",
        choices=["COMPUTE", "VL", "STORAGE", "LINKPORT"],
        required=True
    )
    vduId = serializers.CharField(
        help_text="Reference to the related VDU in the VNFD applicable to this resource.",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    resourceTemplateId = serializers.CharField(
        help_text="Reference to a resource template(such as VnfVirtualLinkDesc) in the VNFD.",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    resource = ResourceHandleSerializer(
        help_text="Resource information for an existing resource.",
        required=False,
        allow_null=True
    )


class ConstraintResourceRefSerializer(serializers.Serializer):
    idType = serializers.ChoiceField(
        help_text="The type of the identifier.",
        choices=["RES_MGMT", "GRANT"],
        required=True
    )
    resourceId = serializers.CharField(
        help_text="An actual resource-management-level identifier(idType=RES_MGMT), or an identifier that references a ResourceDefinition(idType=GRANT).",
        required=True
    )
    vimConnectionId = serializers.CharField(
        help_text="",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifier of the resource provider. It shall only be present when idType = RES_MGMT.",
        required=False,
        allow_null=True,
        allow_blank=True
    )


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


class ResponseDescriptor(serializers.Serializer):
    status = serializers.CharField(help_text="status.", required=True)
    responsehistorylist = serializers.CharField(help_text="History response messages.", required=True)
    responseid = serializers.IntegerField(help_text="Response identifier.", required=True)
    errorcode = serializers.CharField(help_text="Errorcode.", required=True)
    progress = serializers.IntegerField(help_text="Progress.", required=True)
    statusdescription = serializers.CharField(help_text="Status description.", required=True)


class OperationStatusInfo(serializers.Serializer):
    responsedescriptor = ResponseDescriptor(help_text="Response descriptor.", required=True)
    jobid = serializers.CharField(help_text="Job ID.", required=True)


class VnfOperRespSerializer(serializers.Serializer):
    operationStatusInfo = OperationStatusInfo(
        help_text="Operation Status.",
        required=True
    )


class VnfGrantReqSerializer(serializers.Serializer):
    vnfmid = serializers.CharField(help_text="VNFM identifier.", required=True)
    nfvoid = serializers.CharField(help_text="NFVO identifier.", required=True)
    vimid = serializers.CharField(help_text="VIM identifier.", required=True)
    exvimidlist = serializers.CharField(help_text="Extend VIM identifier list.", required=True)
    tenant = serializers.CharField(help_text="Tenant name.", required=True)
    vnfistanceid = serializers.CharField(help_text="VNF instance identifier.", required=True)
    operationright = serializers.CharField(help_text="Operation right.", required=True)
    vmlist = serializers.CharField(help_text="VM list.", required=True)


class VnfGrantRespSerializer(serializers.Serializer):
    vimid = serializers.CharField(help_text="VIM identifier.", required=True)
    tenant = serializers.CharField(help_text="Tenant name.", required=True)


class VnfNotifyReqSerializer(serializers.Serializer):
    nfvoid = serializers.CharField(help_text="NFVO identifier.", required=True)
    vnfmid = serializers.CharField(help_text="VNFM identifier.", required=True)
    vimid = serializers.CharField(help_text="VIM identifier.", required=True)
    timestamp = serializers.CharField(help_text="Timestamp.", required=True)
    vnfistanceid = serializers.CharField(help_text="VNF instance identifier.", required=True)
    eventtype = serializers.CharField(help_text="Event type.", required=True)
    vmlist = serializers.CharField(help_text="VM list.", required=True)
