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

from driver.interfaces.serializers.link import LinkSerializer
from driver.interfaces.serializers.resource_handle import ResourceHandleSerializer

from rest_framework import serializers

LCM_OPERATION_TYPES = [
    "INSTANTIATE",
    "SCALE",
    "SCALE_TO_LEVEL",
    "CHANGE_FLAVOUR",
    "TERMINATE",
    "HEAL",
    "OPERATE",
    "CHANGE_EXT_CONN",
    "MODIFY_INFO"
]


LCM_OPERATION_STATE_TYPES = [
    "STARTING",
    "PROCESSING",
    "COMPLETED",
    "FAILED_TEMP",
    "FAILED",
    "ROLLING_BACK",
    "ROLLED_BACK"
]


VNFCS_CHANGE_TYPES = [
    "ADDED",
    "REMOVED",
    "MODIFIED",
    "TEMPORARY"
]


STORAGES_CHANGE_TYPES = [
    "ADDED",
    "REMOVED",
    "MODIFIED",
    "TEMPORARY"
]


VLS_CHANGE_TYPES = [
    "ADDED",
    "REMOVED",
    "MODIFIED",
    "TEMPORARY",
    "LINK_PORT_ADDED",
    "LINK_PORT_REMOVED"
]


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


class VimConnectionInfoSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="The identifier of the VIM Connection. This identifier is managed by the NFVO.",
        max_length=255,
        required=True,
        allow_null=False,
        allow_blank=False)
    vimId = serializers.CharField(
        help_text="The identifier of the VIM instance. This identifier is managed by the NFVO.",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=True)
    vimType = serializers.CharField(
        help_text="Discriminator for the different types of the VIM information.",
        max_length=255,
        required=True,
        allow_null=False,
        allow_blank=False)
    interfaceInfo = serializers.DictField(
        help_text="Information about the interface or interfaces to the VIM",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True)
    accessInfo = serializers.DictField(
        help_text="Authentication credentials for accessing the VIM, and other access-related information",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True)
    extra = serializers.DictField(
        help_text="VIM type specific additional information. \
        The applicable structure, and whether or not this attribute is available, is dependent on the content of vimType.",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True)


class ProblemDetailsSerializer(serializers.Serializer):
    type = serializers.CharField(help_text="Type", required=False, allow_null=True)
    title = serializers.CharField(help_text="Title", required=False, allow_null=True)
    status = serializers.IntegerField(help_text="Status", required=True)
    detail = serializers.CharField(help_text="Detail", required=True, allow_null=True)
    instance = serializers.CharField(help_text="Instance", required=False, allow_null=True)
    additional_details = serializers.ListField(
        help_text="Any number of additional attributes, as defined in a " +
        "specification or by an implementation.",
        required=False,
        allow_null=True)


class ExtlinkPortInfoSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="Identifier of this link port as provided by the entity that has created the link port.",
        max_length=255,
        required=True,
        allow_blank=False,
        allow_null=False)
    resourceHandle = ResourceHandleSerializer(
        help_text="Reference to the virtualised resource realizing this link port.",
        required=True,
        allow_null=False)
    id = serializers.CharField(
        help_text="Identifier of the external CP of the VNF connected to this link port. \
        There shall be at most one link port associated with any external connection point instance.",
        max_length=255,
        required=False,
        allow_blank=True,
        allow_null=True)


class ExtVirtualLinkInfoSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="Identifier of the external VL and the related external VL information instance. \
        The identifier is assigned by the NFV-MANO entity that manages this VL instance.",
        required=True,
        max_length=255,
        allow_null=False,
        allow_blank=False)
    resourceHandle = ResourceHandleSerializer(
        help_text="Reference to the resource realizing this VL.",
        required=True,
        allow_null=False)
    extlinkPorts = ExtlinkPortInfoSerializer(
        help_text="Link ports of this VL.",
        many=True,
        required=False,
        allow_null=True)


class VnfInfoModificationsSerializer(serializers.Serializer):
    vnfInstanceName = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfInstanceName' attribute in 'VnfInstance'",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=True)
    vnfInstanceDescription = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfInstanceDescription' attribute in 'VnfInstance'",
        required=False,
        allow_null=True,
        allow_blank=True)
    vnfdId = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfdId' attribute in 'VnfInstance'",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=True)
    vnfProvider = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfProvider'  attribute in 'VnfInstance'",
        max_length=255,
        required=False,
        allow_null=True)
    vnfProductName = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfProductName' attribute in 'vnfInstance'",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=True)
    vnfSoftwareVersion = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfSoftwareVersion' attribute in 'VnfInstance'.",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=True)
    vnfdVersion = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfdVersion' attribute in 'VnfInstance'. ",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=False)
    vnfPkgId = serializers.CharField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfPkgId' attribute in 'VnfInstance'.",
        max_length=255,
        required=False,
        allow_null=True,
        allow_blank=False)
    vnfConfigurableProperties = serializers.DictField(
        help_text="If present, this attribute signals modifications of the " +
        "'vnfConfigurableProperties'  attribute in 'VnfInstance'. ",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True,)
    vimConnectionInfo = VimConnectionInfoSerializer(
        help_text="If present, this attribute signals modifications of certain" +
        "entries in the 'vimConnectionInfo'",
        required=False,
        many=True,
        allow_null=True)
    metadata = serializers.DictField(
        help_text="If present, this attribute signals modifications of certain" +
        "'metadata' attribute in 'vnfInstance'.",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True)
    extensions = serializers.DictField(
        help_text="If present, this attribute signals modifications of certain" +
        "'extensions' attribute in 'vnfInstance'.",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True)


class LcmOpLinkSerializer(serializers.Serializer):
    self = LinkSerializer(
        help_text="URI of this resource.",
        required=True,
        allow_null=False)
    vnfInstance = serializers.CharField(
        help_text="Link to the VNF instance that the operation applies to.",
        required=True)
    grant = serializers.CharField(
        help_text="Link to the grant for this operation, if one exists.",
        required=False)
    cancel = serializers.CharField(
        help_text="Link to the task resource that represents the 'cancel' " +
        "operation for this VNF LCM operation occurrence.",
        required=False)
    retry = serializers.CharField(
        help_text="Link to the task resource that represents the 'retry' " +
        "operation for this VNF LCM operation occurrence, if" +
        " retrying is currently allowed",
        required=False)
    rollback = serializers.CharField(
        help_text="Link to the task resource that represents the 'cancel' " +
        "operation for this VNF LCM operation occurrence.",
        required=False)
    fail = serializers.CharField(
        help_text="Link to the task resource that represents the 'fail' " +
        "operation for this VNF LCM operation occurrence.",
        required=False)


class AffectedVnfcsSerializer(serializers.Serializer):
    id = serializers.UUIDField(
        help_text="Identifier of the Vnfc instance, identifying the " +
        "applicable 'vnfcResourceInfo' entry in the 'VnfInstance' data type",
        required=True
    )
    vduId = serializers.UUIDField(
        help_text="Identifier of the related VDU in the VNFD.",
        required=True
    )
    changeType = serializers.ChoiceField(
        help_text="Signals the type of change",
        required=True,
        choices=VNFCS_CHANGE_TYPES
    )
    affectedVnfcCpIds = serializers.ListField(
        help_text="Identifiers of CP(s) of the VNFC instance that " +
        "were affected by the change",
        required=False,
        child=serializers.UUIDField(required=True)
    )
    addedStorageResourceIds = serializers.ListField(
        help_text="References to VirtualStorage resources that " +
        "have been added",
        required=False,
        child=serializers.UUIDField()
    )
    removedStorageResourceIds = serializers.ListField(
        help_text="References to VirtualStorage resources that " +
        "have been removed.",
        required=False,
        child=serializers.UUIDField()
    )
    metadata = serializers.DictField(
        help_text="Metadata about this resource. ",
        required=False,
        allow_null=True)
    computeResource = ResourceHandleSerializer(
        help_text="Reference to the VirtualCompute resource.",
        required=True,
        allow_null=False)


class AffectedStoragesSerializer(serializers.Serializer):
    id = serializers.UUIDField(
        help_text="Identifier of the Storage instance, identifying the " +
        "applicable 'virtualStorageResourceInfo' entry in the 'VnfInstance' data type",
        required=True
    )
    virtualStorageDescId = serializers.UUIDField(
        help_text="Identifier of the related VirtualStorage descriptor " +
        "in the VNFD. ",
        required=True
    )
    changeType = serializers.ChoiceField(
        help_text="Signals the type of change",
        required=True,
        choices=STORAGES_CHANGE_TYPES
    )
    metadata = serializers.DictField(
        help_text="Metadata about this resource. ",
        required=False,
        allow_null=True)
    storageResource = ResourceHandleSerializer(
        help_text="Reference to the VirtualStorage resource.",
        required=True,
        allow_null=False)


class AffectedVLsSerializer(serializers.Serializer):
    id = serializers.UUIDField(
        help_text="Identifier of the virtual link instance, identifying " +
        "the applicable 'vnfVirtualLinkResourceInfo' ",
        required=True
    )
    virtualLinkDescId = serializers.UUIDField(
        help_text="Identifier of the related VLD in the VNFD.",
        required=True
    )
    changeType = serializers.ChoiceField(
        help_text="Signals the type of change",
        required=True,
        choices=VLS_CHANGE_TYPES
    )
    metadata = serializers.DictField(
        help_text="Metadata about this resource. ",
        required=False,
        allow_null=True)
    networkResource = ResourceHandleSerializer(
        help_text="Reference to the VirtualNetwork resource.",
        required=True,
        allow_null=False)


class ResourceChangesSerializer(serializers.Serializer):
    affectedVnfcs = AffectedVnfcsSerializer(
        help_text="Information about VNFC instances that were affected " +
        "during the lifecycle operation.",
        required=False,
        many=True
    )
    affectedVirtualLinks = AffectedVLsSerializer(
        help_text="Information about VL instances that were affected " +
        "during the lifecycle operation. ",
        required=False,
        many=True
    )
    affectedVirtualStorages = AffectedStoragesSerializer(
        help_text="Information about virtualised storage instances that " +
        "were affected during the lifecycle operation",
        required=False,
        many=True
    )


class VNFLCMOpOccSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="Identifier of this VNF lifecycle management operation" +
        "occurrence,",
        max_length=255,
        required=True,
        allow_null=False
    )
    operationState = serializers.ChoiceField(
        help_text="The state of the VNF LCM operation occurrence. ",
        required=True,
        choices=LCM_OPERATION_STATE_TYPES
    )
    stateEnteredTime = serializers.CharField(
        help_text="Date-time when the current state was entered.",
        max_length=50
    )
    startTime = serializers.CharField(
        help_text="Date-time of the start of the operation.",
        max_length=50
    )
    vnfInstanceId = serializers.UUIDField(
        help_text="Identifier of the VNF instance to which the operation" +
        "applies"
    )
    grantId = serializers.UUIDField(
        help_text="Identifier of the grant related to this VNF LCM operation " +
                  "occurrence, if such grant exists.",
        allow_null=True
    )
    operation = serializers.ChoiceField(
        help_text="The lifecycle management operation",
        required=True,
        choices=LCM_OPERATION_TYPES
    )
    isAutomaticInvocation = serializers.BooleanField(
        help_text="Set to true if this VNF LCM operation occurrence has " +
        "been triggered by an automated procedure inside the VNFM. " +
        "Set to False otherwise.",
        default=False
    )
    operationParams = serializers.DictField(
        help_text="Input parameters of the LCM operation. This attribute " +
        "shall be formatted according to the request data type of the " +
        "related LCM operation. The following mapping between operationType and the " +
        "data type of this attribute shall apply: " +
        "1. INSTANTIATE: InstantiateVnfRequest" +
        "2. SCALE: ScaleVnfRequest " +
        "3. SCALE_TO_LEVEL: ScaleVnfToLevelRequest " +
        "4. CHANGE_FLAVOUR: ChangeVnfFlavourRequest " +
        "5. OPERATE: OperateVnfRequest " +
        "6. HEAL: HealVnfRequest " +
        "7. CHANGE_EXT_CONN: ChangeExtVnfConnectivityRequest " +
        "8. TERMINATE: TerminateVnfRequest " +
        "9. MODIFY_INFO: VnfInfoModifications",
        required=True,
        allow_null=False
    )
    isCancelPending = serializers.BooleanField(
        help_text="If the VNF LCM operation occurrence is in 'STARTING'" +
        "'PROCESSING' or 'ROLLING_BACK' state and the operation is being" +
        " cancelled, this attribute shall be set to True. Otherwise, " +
        " it shall be set to False.",
        required=True
    )
    cancelMode = serializers.CharField(
        help_text="The mode of an ongoing cancellation. Shall be present " +
        "when isCancelPending=true, and shall be None otherwise.",
        allow_null=True,
        required=False
    )
    error = ProblemDetailsSerializer(
        help_text="If 'operationState' is 'FAILED_TEMP' or 'FAILED' or " +
        "'PROCESSING' or 'ROLLING_BACK' and previous value of 'operationState' " +
        "was 'FAILED_TEMP'  this attribute shall be present ",
        allow_null=True,
        required=False
    )
    resourceChanges = ResourceChangesSerializer(
        help_text="It contains information about the cumulative changes " +
        "to virtualised resources that were performed so far by the LCM " +
        "operation since its start, if applicable.",
        required=False,
        allow_null=True)
    changedInfo = VnfInfoModificationsSerializer(
        help_text="Information about the changed VNF instance information, " +
        "including VNF configurable properties",
        required=False,
        allow_null=True)
    changedExtConnectivity = ExtVirtualLinkInfoSerializer(
        help_text="Information about changed external connectivity, if this " +
        "notification represents the result of a lifecycle operation occurrence. " +
        "Shall be present if the 'notificationStatus' is set to 'RESULT' and the " +
        "'operation' is set to 'CHANGE_EXT_CONN'. Shall be absent otherwise.",
        many=True,
        required=False,
        allow_null=True)
    _links = LcmOpLinkSerializer(
        help_text="Links to resources related to this resource.",
        required=True)
