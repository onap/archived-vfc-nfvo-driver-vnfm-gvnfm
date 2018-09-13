# Copyright 2017 ZTE Corporation.
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

from django.conf.urls import url
from driver.interfaces.views import VnfInstInfo, VnfTermInfo, VnfQueryInfo, VnfOperInfo
from driver.interfaces.views import Subscription
from driver.interfaces.views import VnfPkgsInfo, VnfGrantInfo, VnfNotifyInfo, QuerySingleVnfLcmOpOcc

urlpatterns = [
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/(?P<vnfmid>[0-9a-zA-Z\-\_]+)/vnfs$', VnfInstInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/(?P<vnfmid>[0-9a-zA-Z\-\_]+)/vnfs/(?P<vnfInstanceId>[0-9a-zA-Z\-\_]+)/terminate$', VnfTermInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/(?P<vnfmid>[0-9a-zA-Z\-\_]+)/vnfs/(?P<vnfInstanceId>[0-9a-zA-Z\-\_]+)$', VnfQueryInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/(?P<vnfmid>[0-9a-zA-Z\-\_]+)/jobs/(?P<jobid>[0-9a-zA-Z\-\_]+)$', VnfOperInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/subscriptions$', Subscription.as_view()),


    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/vnfpackages$', VnfPkgsInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/resource/grant$', VnfGrantInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/vnfs/lifecyclechangesnotification$', VnfNotifyInfo.as_view()),
    url(r'^api/(?P<vnfmtype>[0-9a-zA-Z\-\_]+)/v1/(?P<vnfmid>[0-9a-zA-Z\-\_]+)/vnf_lcm_op_occs/(?P<lcmopoccid>[0-9a-zA-Z_-]+)$', QuerySingleVnfLcmOpOcc.as_view()),
]
