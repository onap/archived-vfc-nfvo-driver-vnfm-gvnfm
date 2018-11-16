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

from rest_framework import serializers


class VimConnectionSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="The identifier of the VIM Connection. This identifier is managed by the NFVO.",
        required=True
    )
    vimId = serializers.CharField(
        help_text="The identifier of the VIM instance. This identifier is managed by the NFVO.",
        required=False,
        allow_null=True
    )
    vimType = serializers.CharField(
        help_text="Discriminator for the different types of the VIM information.",
        required=False,
        allow_null=True
    )
    interfaceInfo = serializers.DictField(
        help_text="Information about the interface or interfaces to the VIM.",
        child=serializers.CharField(help_text="Interface Info", allow_blank=True),
        required=False,
        allow_null=True
    )
    accessInfo = serializers.DictField(
        help_text="Authentication credentials for accessing the VIM.",
        child=serializers.CharField(help_text="Access Info", allow_blank=True),
        required=False,
        allow_null=True
    )
    extra = serializers.DictField(
        help_text="VIM type specific additional information.",
        child=serializers.CharField(help_text="Extra", allow_blank=True),
        required=False,
        allow_null=True
    )


class ZoneInfoSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="The identifier of this ZoneInfo instance, for the purpose of referencing it from other structures in the Grant structure.",
        required=True
    )
    zoneId = serializers.CharField(
        help_text="The identifier of the resource zone, as managed by the resource management layer(typically, the VIM).",
        required=False
    )
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the connection to the VIM that manages the resource zone.",
        required=False
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifies the entity responsible for the management the resource zone.",
        required=False
    )


class ZoneGroupInfoSerializer(serializers.Serializer):
    zoneId = serializers.ListSerializer(
        help_text="References of identifiers of ZoneInfo structures.",
        child=serializers.CharField(help_text="IdentifierLocal", allow_blank=True),
        required=False
    )


class GrantInfoSerializer(serializers.Serializer):
    resourceDefinitionId = serializers.CharField(
        help_text="Identifier of the related ResourceDefinition from the related GrantRequest.",
        required=True
    )
    reservationId = serializers.CharField(
        help_text="The reservation identifier applicable to the VNFC/VirtualLink/VirtualStorage.",
        required=False
    )
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the VIM connection to be used to manage this resource.",
        required=False,
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifies the entity responsible for the management of the virtualised resource.",
        required=False
    )
    zoneId = serializers.CharField(
        help_text="Reference to the identifier of the ZoneInfo in the Grant.",
        required=False
    )
    resourceGroupId = serializers.CharField(
        help_text="Identifier of the infrastructure resource group.",
        required=False
    )


class VimComputeResourceFlavourSerializer(serializers.Serializer):
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the VIM connection to access the flavour referenced in this structure.",
        required=False
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifies the entity responsible for the management of the virtualised resource.",
        required=False,
    )
    vnfdVirtualComputeDescId = serializers.CharField(
        help_text="Identifier which references the virtual compute descriptor in the VNFD that maps to this flavour.",
        required=False
    )
    vimFlavourId = serializers.CharField(
        help_text="Identifier of the compute resource flavour in the resource management layer (i.e. VIM).",
        required=False
    )


class VimSoftwareImageSerializer(serializers.Serializer):
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the VIM connection to access the flavour referenced in this structure.",
        required=False
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifies the entity responsible for the management of the virtualised resource.",
        required=False
    )
    vnfdSoftwareImageId = serializers.CharField(
        help_text="Identifier which references the software image descriptor in the VNFD.",
        required=False
    )
    vimSoftwareImageId = serializers.CharField(
        help_text="Identifier of the software image in the resource management layer (i.e. VIM).",
        required=False
    )


class VimAssetsSerializer(serializers.Serializer):
    computeResourceFlavours = VimComputeResourceFlavourSerializer(
        help_text="Mappings between virtual compute descriptors defined in the VNFD and compute resource flavours managed in the VIM.",
        many=True,
        required=False
    )
    softwareImages = VimSoftwareImageSerializer(
        help_text="Mappings between software images defined in the VNFD and software images managed in the VIM.",
        many=True,
        required=False
    )


class AddressRangeSerializer(serializers.Serializer):
    minAddress = serializers.CharField(
        help_text="Lowest IP address belonging to the range.",
        required=True
    )
    maxAddress = serializers.CharField(
        help_text="Highest IP address belonging to the range.",
        required=True
    )


class IpAddresseSerializer(serializers.Serializer):
    type = serializers.ChoiceField(
        help_text="The type of the IP addresses.",
        choices=["IPV4", "IPV6"],
        required=True
    )
    fixedAddresses = serializers.ListSerializer(
        help_text="Fixed addresses to assign.",
        child=serializers.CharField(help_text="IpAddress"),
        required=False
    )
    numDynamicAddresses = serializers.IntegerField(
        help_text="Number of dynamic addresses to assign.",
        required=True
    )
    addressRange = AddressRangeSerializer(
        help_text="An IP address range to be used, e.g. in case of egress connections.",
        required=False
    )
    subnetId = serializers.CharField(
        help_text="Subnet defined by the identifier of the subnet resource in the VIM.",
        required=False
    )


class IpOverEthernetAddressDataSerializer(serializers.Serializer):
    macAddress = serializers.CharField(
        help_text="MAC address.",
        required=False
    )
    ipAddresses = IpAddresseSerializer(
        help_text="List of IP addresses to assign to the CP instance.",
        many=True,
        required=False
    )


class CpProtocolDataSerializer(serializers.Serializer):
    layerProtocol = serializers.ChoiceField(
        help_text="Identifier of layer(s) and protocol(s).",
        choices=["IP_OVER_ETHERNET"],
        required=True
    )
    ipOverEthernet = IpOverEthernetAddressDataSerializer(
        help_text="Network address data for IP over Ethernet to assign to the extCP instance.",
        required=False,
    )


class VnfExtCpConfigSerializer(serializers.Serializer):
    cpInstanceId = serializers.CharField(
        help_text="Identifier of the external CP instance to which this set of configuration parameters is requested to be applied.",
        required=False
    )
    linkPortId = serializers.CharField(
        help_text="Identifier of a pre-configured link port to which the external CP will be associated.",
        required=False
    )
    cpProtocolData = CpProtocolDataSerializer(
        help_text="Parameters for configuring the network protocols on the link port that connects the CP to a VL.",
        many=True
    )


class VnfExtCpDataSerializer(serializers.Serializer):
    cpdId = serializers.CharField(
        help_text="The identifier of the CPD in the VNFD.",
        required=True
    )
    cpConfig = VnfExtCpConfigSerializer(
        help_text="List of instance data that need to be configured on the CP instances created from the respective CPD.",
        many=True,
        required=False
    )


class ExtLinkPortDataSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="Identifier of this link port as provided by the entity that has created the link port.",
        required=True
    )
    resourceHandle = serializers.CharField(
        help_text="Reference to the virtualised resource realizing this link port.",
        required=True
    )


class ExtVirtualLinkDataSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="The identifier of the external VL instance.",
        required=True
    )
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the VIM connection to manage this resource.",
        required=False
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifies the entity responsible for the management of this resource.",
        required=False
    )
    resourceId = serializers.CharField(
        help_text="The identifier of the resource in the scope of the VIM or the resource provider.",
        required=True
    )
    extCps = VnfExtCpDataSerializer(
        help_text="External CPs of the VNF to be connected to this external VL.",
        many=True,
        required=False
    )
    extLinkPorts = ExtLinkPortDataSerializer(
        help_text="Externally provided link ports to be used to connect external connection points to this external VL.",
        many=True,
        required=False
    )


class ExtManagedVirtualLinkDataSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="The identifier of the externally-managed internal VL instance.",
        required=True
    )
    virtualLinkDescId = serializers.CharField(
        help_text="The identifier of the VLD in the VNFD for this VL.",
        required=True
    )
    vimConnectionId = serializers.CharField(
        help_text="Identifier of the VIM connection to manage this resource.",
        required=False
    )
    resourceProviderId = serializers.CharField(
        help_text="Identifies the entity responsible for the management of this resource.",
        required=False
    )
    resourceId = serializers.CharField(
        help_text="The identifier of the resource in the scope of the VIM or the resource provider.",
        required=True
    )


class GrantLinksSerializer(serializers.Serializer):
    self = LinkSerializer(
        help_text="URI of this resource.",
        required=True
    )
    vnfLcmOpOcc = LinkSerializer(
        help_text="Related VNF lifecycle management operation occurrence.",
        required=True
    )
    vnfInstance = LinkSerializer(
        help_text="Related VNF instance.",
        required=True
    )


class GrantSerializer(serializers.Serializer):
    id = serializers.CharField(
        help_text="Identifier of the grant.",
        required=True
    )
    vnfInstanceId = serializers.CharField(
        help_text="Identifier of the related VNF instance.",
        required=True
    )
    vnfLcmOpOccId = serializers.CharField(
        help_text="Identifier of the related VNF lifecycle management operation occurrence.",
        required=True,
    )
    vimConnections = VimConnectionSerializer(
        help_text="Provides information regarding VIM connections that are approved to be used by the VNFM to allocate resources.",
        many=True,
        required=False
    )
    zones = ZoneInfoSerializer(
        help_text="Identifies resource zones where the resources are approved to be allocated by the VNFM.",
        many=True,
        required=False
    )
    zoneGroups = ZoneGroupInfoSerializer(
        help_text="Information about groups of resource zones.",
        many=True,
        required=False
    )
    computeReservationId = serializers.CharField(
        help_text="Information that identifies a reservation applicable to the compute resource requirements.",
        required=False,
    )
    networkReservationId = serializers.CharField(
        help_text="Information that identifies a reservation applicable to the network resource requirements.",
        required=False,
    )
    storageReservationId = serializers.CharField(
        help_text="Information that identifies a reservation applicable to the storage resource requirements.",
        required=False,
    )
    addResources = GrantInfoSerializer(
        help_text="List of resources that are approved to be added.",
        many=True,
        required=False
    )
    tempResources = GrantInfoSerializer(
        help_text="List of resources that are approved to be temporarily instantiated during the runtime of the lifecycle operation.",
        many=True,
        required=False
    )
    removeResources = GrantInfoSerializer(
        help_text="List of resources that are approved to be removed.",
        many=True,
        required=False
    )
    updateResources = GrantInfoSerializer(
        help_text="List of resources that are approved to be modified.",
        many=True,
        required=False
    )
    vimAssets = VimAssetsSerializer(
        help_text="Information about assets for the VNF that are managed by the NFVO in the VIM.",
        required=False,
    )
    extVirtualLinks = ExtVirtualLinkDataSerializer(
        help_text="Information about external VLs to connect the VNF to.",
        many=True,
        required=False
    )
    extManagedVirtualLinks = ExtManagedVirtualLinkDataSerializer(
        help_text="Information about internal VLs that are managed by other entities than the VNFM.",
        many=True,
        required=False
    )
    additionalParams = serializers.DictField(
        help_text="Additional parameters passed by the NFVO, \
        specific to the VNF and the LCM operation.",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
    )
    _links = GrantLinksSerializer(
        help_text="Links to resources related to this resource.",
        required=True
    )
