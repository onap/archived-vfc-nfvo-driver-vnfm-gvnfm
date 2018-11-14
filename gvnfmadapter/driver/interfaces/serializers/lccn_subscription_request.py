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

from driver.interfaces.serializers.lccn_filter import LifeCycleChangeNotificationsFilterSerializer
from driver.interfaces.serializers.subscription_authentication import SubscriptionAuthenticationSerializer


class LccnSubscriptionRequestSerializer(serializers.Serializer):
    callbackUri = serializers.CharField(
        help_text="The URI of the endpoint to send the notification to.",
        required=True,
        allow_null=False)
    filter = LifeCycleChangeNotificationsFilterSerializer(
        help_text="Filter settings for the subscription, to define the subset of all " +
                  "notifications this subscription relates to.",
        required=False,
        allow_null=True)
    authentication = SubscriptionAuthenticationSerializer(
        help_text="Authentication parameters to configure the use of Authorization when sending " +
                  "notifications corresponding to this subscription.",
        required=False,
        allow_null=True)
