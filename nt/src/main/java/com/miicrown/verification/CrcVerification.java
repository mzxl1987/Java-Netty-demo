package com.miicrown.verification;

public class CrcVerification implements Verification {

	@Override
	public Object create(Object data) {
		if(data instanceof byte[]){
			byte[] b = (byte[]) data;
			if(null != b && b.length > 0){
				return (byte[])data;
			}else{
				return null;
			}
		}	
		return null;
	}

	@Override
	public boolean compare(Object data, Object dest) {
		Object result = create(data); 
		if(null != result 
				&& result instanceof byte[] 
				&& dest instanceof byte[]){
			
			byte[] tmp_s = (byte[])result;
			byte[] tmp_d = (byte[])dest;
			
			for(int i = 0, c = tmp_d.length; i < c; i++){
				if(tmp_s[i] != tmp_d[i])    return false;
			}
			
			return true;
		}
		
		return false;
	}

}
