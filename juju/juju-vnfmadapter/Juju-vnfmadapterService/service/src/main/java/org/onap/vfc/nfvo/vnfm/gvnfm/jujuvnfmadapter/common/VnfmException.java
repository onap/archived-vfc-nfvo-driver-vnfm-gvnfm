/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

/**
 * 
 * Vnfm exception class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class VnfmException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since  NFVO 0.5
     */
    public VnfmException() {
        super();
    }

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param message
     * @param cause
     * @since  NFVO 0.5
     */
    public VnfmException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param message
     * @since  NFVO 0.5
     */
    public VnfmException(String message) {
        super(message);
    }

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param cause
     * @since  NFVO 0.5
     */
    public VnfmException(Throwable cause) {
        super(cause);
    }

}
