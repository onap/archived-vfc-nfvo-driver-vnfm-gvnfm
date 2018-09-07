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


class VnfOperateRequestSerializer(serializers.Serializer):
    changeStateTo = serializers.ChoiceField(
        help_text="The desired operational state (i.e. started or stopped) to change the VNF to.",
        choices=["STARTED", "STOPPED"],
        required=True)
    stopType = serializers.ChoiceField(
        help_text="It signals whether forceful or graceful stop is requested.",
        choices=["FORCEFUL", "GRACEFUL"],
        required=False)
    gracefulStopTimeout = serializers.IntegerField(
        help_text="The time interval to wait for the VNF to be taken out of service during graceful stop.",
        required=False)
    additionalParams = serializers.DictField(
        help_text="Additional input parameters for the operate process, \
        specific to the VNF being operated, \
        as declared in the VNFD as part of OperateVnfOpConfig.",
        child=serializers.CharField(help_text="", allow_blank=True),
        required=False,
        allow_null=True)


class ProblemDetailsSerializer(serializers.Serializer):
    type = serializers.CharField(help_text="Type", required=False, allow_null=True)
    title = serializers.CharField(help_text="Title", required=False, allow_null=True)
    status = serializers.IntegerField(help_text="Status", required=True)
    detail = serializers.CharField(help_text="Detail", required=True, allow_null=True)
    instance = serializers.CharField(help_text="Instance", required=False, allow_null=True)


class AffectedVm(serializers.Serializer):
    vmid = serializers.CharField(help_text="Vm id", required=True)
    vduid = serializers.CharField(help_text="Vdu id", required=True)
    vmname = serializers.CharField(help_text="Vm name", required=True)


class VnfHealRequestSerializer(serializers.Serializer):
    action = serializers.CharField(help_text="Action for NS heal", required=True, allow_null=True)
    affectedvm = AffectedVm(help_text="Get the vm information to be healed", required=True)
