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


class ProblemDetailsSerializer(serializers.Serializer):
    type = serializers.CharField(
        help_text='A URI reference according to IETF RFC 3986 [5] that identifies the problem type.',
        required=False
    )
    title = serializers.CharField(
        help_text='A short, human-readable summary of the problem type. \
        It should not change from occurrence to occurrence of the problem, \
        except for purposes of localization.',
        required=False,
    )
    status = serializers.IntegerField(
        help_text='The HTTP status code for this occurrence of the problem.',
        required=True
    )
    detail = serializers.CharField(
        help_text='A human-readable explanation specific to this occurrence of the problem.',
        required=True
    )
    instance = serializers.CharField(
        help_text='A URI reference that identifies the specific occurrence of the problem. \
        It may yield further information if dereferenced.',
        required=False
    )
    additional_details = serializers.ListField(
        help_text='Any number of additional attributes, \
        as defined in a specification or by an implementation.',
        required=False,
    )
