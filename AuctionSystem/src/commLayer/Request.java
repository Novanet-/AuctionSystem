package commLayer;


public class Request
{
	private RequestType requestType;
	private String requestParameter;
	
	/**
	 * @param requestType
	 * @param requestParameter
	 */
	public Request(RequestType requestType, String requestParameter)
	{
		super();
		this.requestType = requestType;
		this.requestParameter = requestParameter;
	}

	
	public RequestType getRequestType()
	{
		return requestType;
	}

	
	public String getRequestParameter()
	{
		return requestParameter;
	}

	


}
