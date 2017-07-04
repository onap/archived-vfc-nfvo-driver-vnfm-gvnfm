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
package org.openo.nfvo.jujuvnfmadapter.service.adapter.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ProcessMockImpl extends Process  {

	@Override
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos;
		/*try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(new Object());
			oos.flush();
		    oos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

	    InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	@Override
	public InputStream getErrorStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int waitFor() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int exitValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
