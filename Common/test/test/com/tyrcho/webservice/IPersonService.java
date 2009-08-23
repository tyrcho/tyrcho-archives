package test.com.tyrcho.webservice;

import javax.jws.WebService;

@WebService
public interface IPersonService {
	Person computeFullName(Person p);
}