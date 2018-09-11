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


class PlacementConstraintSerializer(serializers.Serializer):
    affinityOrAntiAffinity = serializers.ChoiceField(
        help_text="The type of the constraint.",
        choices=["AFFINITY", "ANTI_AFFINITY"],
        required=True
    )
    scope = serializers.ChoiceField(
        help_text="The scope of the placement constraint indicating the category of the place where the constraint applies.",
        choices=["NFVI_POP", "ZONE", "ZONE_GROUP", "NFVI_NODE"],
        required=True
    )
    resource = ConstraintResourceRefSerializer(
        help_text="References to resources in the constraint rule.",
        many=True,
        required=False
    )


class VimConstraintSerializer(serializers.Serializer):
    sameResourceGroup = serializers.BooleanField(
        help_text="Set to true when the constraint applies not only to the same VIM connection, but also to the same infrastructure resource group.",
        required=False
    )
    resource = ConstraintResourceRefSerializer(
        help_text="References to resources in the constraint rule.",
        many=True,
        required=False
    )


class GrantRequestLinksSerializer(serializers.Serializer):
    vnfLcmOpOcc = LinkSerializer(
        help_text="Related VNF lifecycle management operation occurrence.",
        required=True
    )
    vnfInstance = LinkSerializer(
        help_text="Related VNF instance.",
        required=True
    )


class GrantRequestSerializer(serializers.Serializer):
    vnfInstanceId = serializers.CharField(
        help_text="Identifier of the VNF instance which this grant request is related to.",
        required=True
    )
    vnfLcmOpOccId = serializers.CharField(
        help_text="The identifier of the VNF lifecycle management operation occurrence associated to the GrantRequest.",
        required=False,  # TODO required
        allow_null=True,
        allow_blank=True
    )
    vnfdId = serializers.CharField(
        help_text="Identifier of the VNFD that defines the VNF for which the LCM operation is to be granted.",
        required=False,  # TODO required
        allow_null=True,
        allow_blank=True
    )
    flavourId = serializers.CharField(
        help_text="Identifier of the VNF deployment flavour of the VNFD that defines the VNF for which the LCM operation is to be granted.",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    operation = serializers.ChoiceField(
        help_text="The lifecycle management operation for which granting is requested.",
        choices=["INSTANTIATE", "SCALE", "SCALE_TO_LEVEL", "CHANGE_FLAVOUR", "TERMINATE", "HEAL", "OPERATE", "CHANGE_EXT_CONN", "MODIFY_INFO"],
        required=True
    )
    isAutomaticInvocation = serializers.BooleanField(
        help_text="Set to true if this VNF LCM operation occurrence has been triggered by an automated procedure inside the VNFM, set to false otherwise.",
        required=True
    )
    instantiationLevelId = serializers.CharField(
        help_text="If operation=INSTANTIATE, the identifier of the instantiation level may be provided as an alternative way to define the resources to be added.",
        required=False,
        allow_null=True,
        allow_blank=True
    )
    addResources = ResourceDefinitionSerializer(
        help_text="List of resource definitions in the VNFD for resources to be added by the LCM operation.",
        many=True,
        required=False
    )
    tempResources = ResourceDefinitionSerializer(
        help_text="List of resource definitions in the VNFD for resources to be temporarily instantiated during the runtime of the LCM operation.",
        many=True,
        required=False
    )
    removeResources = ResourceDefinitionSerializer(
        help_text="Provides the definitions of resources to be removed by the LCM operation.",
        many=True,
        required=False
    )
    updateResources = ResourceDefinitionSerializer(
        help_text="Provides the definitions of resources to be modified by the LCM operation.",
        many=True,
        required=False
    )
    placementConstraints = PlacementConstraintSerializer(
        help_text="Placement constraints that the VNFM may send to the NFVO in order to influence the resource placement decision.",
        many=True,
        required=False
    )
    vimConstraints = VimConstraintSerializer(
        help_text="Used by the VNFM to require that multiple resources are managed through the same VIM connection.",
        many=True,
        required=False
    )
    additionalParams = serializers.DictField(
        help_text="Additional parameters passed by the VNFM.",
        child=serializers.CharField(help_text="KeyValue Pairs", allow_blank=True),
        required=False,
        allow_null=True
    )
    _links = GrantRequestLinksSerializer(
        help_text="Links to resources related to this request.",
        required=False  # TODO required
    )
