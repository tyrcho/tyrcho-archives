package test.com.tyrcho.webservice;

import javax.jws.WebService;

@WebService(targetNamespace="urn:tyrcho.com:person", portName="personPort", serviceName="PersonService")
public class PersonService implements IPersonService {
	/* (non-Javadoc)
	 * @see test.com.tyrcho.webservice.IPersonService#computeFullName(test.com.tyrcho.webservice.Person)
	 */
	public Person computeFullName(Person p) {
		p.setFullName(p.getName()+" "+p.getLastName());
		return p;
	}
	
	
}
