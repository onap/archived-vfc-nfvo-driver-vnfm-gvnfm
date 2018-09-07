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
