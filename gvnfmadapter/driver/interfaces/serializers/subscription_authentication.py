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

AUTH_TYPES = [
    "BASIC",
    "OAUTH2_CLIENT_CREDENTIALS",
    "TLS_CERT"
]


class OAuthCredentialsSerializer(serializers.Serializer):
    clientId = serializers.CharField(
        help_text="Client identifier to be used in the access token \
        request of the OAuth 2.0 client credentials grant type.",
        max_length=255,
        required=False
    )
    clientPassword = serializers.CharField(
        help_text="Client password to be used in the access token \
        request of the OAuth 2.0 client credentials grant type.",
        max_length=255,
        required=False
    )
    tokenEndpoint = serializers.CharField(
        help_text="The token endpoint \
        from which the access token can be obtained.",
        max_length=255,
        required=False
    )


class BasicAuthSerializer(serializers.Serializer):
    userName = serializers.CharField(
        help_text="Username to be used in HTTP Basic authentication.",
        max_length=255,
        required=False
    )
    password = serializers.CharField(
        help_text="Password to be used in HTTP Basic authentication.",
        max_length=255,
        required=False
    )


class SubscriptionAuthenticationSerializer(serializers.Serializer):
    authType = serializers.ListField(
        child=serializers.ChoiceField(required=True, choices=AUTH_TYPES),
        help_text="Defines the types of Authentication / Authorization \
        which the API consumer is willing to accept \
        when receiving a notification.",
        required=True
    )
    paramsBasic = BasicAuthSerializer(
        help_text="Parameters for authentication/authorization using BASIC.",
        required=False
    )
    paramsOauth2ClientCredentials = OAuthCredentialsSerializer(
        help_text="Parameters for authentication/authorization \
        using OAUTH2_CLIENT_CREDENTIALS.",
        required=False
    )
