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


class AffectedVm(serializers.Serializer):
    vmid = serializers.CharField(help_text="Vm id", required=True)
    vduid = serializers.CharField(help_text="Vdu id", required=True)
    vmname = serializers.CharField(help_text="Vm name", required=True)

class VnfHealRequestSerializer(serializers.Serializer):
    action = serializers.CharField(help_text="Action for NS heal", required=True, allow_null=True)
    affectedvm = AffectedVm(help_text="Get the vm information to be healed", required=True)

class HealVnfRequestSerializerToVnfm(serializers.Serializer):
    cause = serializers.CharField(help_text="Cause of NS heal", required=False, allow_null=True)
    additionalParams = serializers.DictField(
        help_text="Additional input parameters for the healing process, \
        specific to the VNF being healed, \
        as declared in the VNFD as part of HealVnfOpConfig.",
        required=False,
        allow_null=True)
