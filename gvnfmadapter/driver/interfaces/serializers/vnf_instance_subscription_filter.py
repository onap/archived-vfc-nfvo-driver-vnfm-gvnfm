# Copyright (C) 2018 Verizon. All Rights Reserved
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


class VersionsSerializer(serializers.Serializer):
    vnfSoftwareVersion = serializers.CharField(
        help_text="Software version to match.",
        max_length=255,
        required=True
    )
    vnfdVersions = serializers.ListField(
        help_text="Match VNF instances \
        that belong to VNF products with certain VNFD versions",
        child=serializers.CharField(max_length=255, required=True),
        required=False
    )


class VnfProductsSerializer(serializers.Serializer):
    vnfProductName = serializers.CharField(
        help_text="Name of the VNF product to match.",
        max_length=255,
        required=True
    )
    versions = VersionsSerializer(
        help_text="Match VNF instances \
        that belong to VNF products with certain versions \
        and a certain product name, from one particular provider.",
        required=False
    )


class VnfProductsProvidersSerializer(serializers.Serializer):
    vnfProvider = serializers.CharField(
        help_text="Name of the VNF provider to match.",
        max_length=255,
        required=True
    )
    vnfProducts = VnfProductsSerializer(
        help_text="match VNF instances that belong to VNF products " +
        "with certain product names, from one particular provider",
        required=False
    )


class VnfInstanceSubscriptionFilterSerializer(serializers.Serializer):
    vnfdIds = serializers.ListField(
        help_text="VNF instances that were created \
        based on a VNFD identified by one of the vnfdId values",
        child=serializers.UUIDField(),
        required=False
    )
    vnfInstanceIds = serializers.ListField(
        help_text="VNF instance IDs that has to be matched",
        child=serializers.UUIDField(),
        required=False
    )
    vnfInstanceNames = serializers.ListField(
        help_text="VNF Instance names that has to be matched",
        child=serializers.CharField(max_length=255, required=True),
        required=False
    )
    vnfProductsFromProviders = VnfProductsProvidersSerializer(
        help_text="match VNF instances \
        that belong to VNF products from certain providers.",
        required=False
    )
