package rest.helpers;

import org.apache.log4j.Logger;

public class GenRequest implements HttpRequestCallback {
	private boolean haserrors;
	private String response = null;
	private Throwable respexcept;
	private Logger logger = Logger.getLogger(HttpRequestUtil.class.getName());
	private String endpoint = null;
	/**
     * respond with status of request object if it has errors.
     * return true or false, true means there are errors.
     * 
     */
	public GenRequest(){}
	public GenRequest(String endpoint){
		this.endpoint = endpoint;
	}
	
	public String get(String path){
		int retries = 30;
		int i = 0;
		if(endpoint != null){
			while(i++ < retries){
				try{
					HttpRequestUtil.makeRequest(endpoint + path, null, HttpRequestType.GET, this);
					if(this.hasError())
					{
						Thread.sleep(10000);//10 seconds, a total of 5 min's
						logger.debug("waiting before retrying " + i);
						if(i == 30){// on the last iteration, log the error
							logger.error("Errors in make request, retries were unsuccessful", respexcept);
						}
					}else{
						break;
					}
				}catch(Exception e){
					logger.error("Exception caught while calling makeRequest " + endpoint + path, e);
				}
			}
			return response;
		}else{
			return null;
		}
			
	}
	
	public String post(String path, String data){
		int retries = 30;
		int i = 0;
		if(endpoint != null){
			while(i++ < retries){
				try{
					//String tmp = "[\"graeme.leach\",\"donatella.fava\",\"ana-belen.gonzalvo\",\"mark.kotlar\",\"joe.farmer\",\"patrick.cox\",\"kathy.belman\",\"bill.lawler\",\"sukanya\",\"joseph.celona\",\"jing-sheng.yu\",\"peter.moczo\",\"kalyan.bojja\",\"mikako.dewa\",\"jbecerra\",\"joseph.binod\",\"katarzyna.bulat\",\"jeffrey.w.jacobs\",\"vasanth.kumar\",\"jeff.nevill\",\"hyoung-joon.jun\",\"ya.han\",\"jean.frerichs\",\"diego.rodriguez.garcia\",\"carsten.ochtrup\",\"ravinder.kalro\",\"cheryl.e.harris\",\"yvonne.clinton\",\"pushpa\",\"ramya.rao\",\"ricardo.sengenberger\",\"rodolfo.concia\",\"pawel.wojtowicz\",\"andrzej.madrzak\",\"andreas.van-arkel\",\"rahul.sharma5\",\"gabriele.sopella\",\"joachim.flierdt\",\"anoop.george\",\"craig.c.cartwright\",\"eric.de.heer\",\"rodrigo.traid-fernandez\",\"zahraa.mohamed-kishk\",\"jermeen.nasr\",\"beenamol.b\",\"santosh.kumar12\",\"sarah.magdi-ahmed-elghayaty\",\"guenter.hoffmann\",\"torsten.wolff\",\"X9314390\",\"X7857590\",\"X7785220\",\"X6054480\",\"X6387140\",\"X7919530\",\"X6762410\",\"X6985650\",\"daniel.hathaway\",\"sourav.chatterjee\",\"anuradha.girish\",\"mats.fredman\",\"dattaraj.joshi\",\"prabhu.eshwarla\",\"antoine.ghaly\",\"anja.heinrichs\",\"ioannis.potikoglou\",\"divya.aggarwal\",\"souvik.basu\",\"narayana-prasad.bv\",\"prasanna.gs\",\"varuna.kamila\",\"kunal.mishra\",\"harsha.narla\",\"p.devaraj\",\"naresh.s\",\"sanjeev-s.kumar\",\"sai.rajeshrv\",\"srinivas.sarangapani\",\"sreenivasulu.chindanoor\",\"ravishankarv\",\"yvonne.boerstler\",\"jdostal\",\"arjun.bailey\",\"shourabh.chatterji\",\"suman.saket\",\"chandrakala.santosh\",\"rudolf.hagedorn\",\"X8740440\",\"terry.reid\",\"barbara.yeats\",\"X6745560\",\"simon.brook\",\"coenraad.viljoen\",\"martin.horvath\",\"seethalakshmi.mani\",\"surjasikha.ganguli\",\"iris.borders\",\"kisun.baik\",\"X4723010\",\"gowrisankar.sadhasivam\",\"yong-kui.lu\",\"shike.lv\",\"bill.kidd\",\"gaikun.mei\",\"darshan.h-v\",\"mousumi.khatua\",\"ravi.ranjan2\",\"kislay.sharma\",\"murali.basam\",\"venkateswara.battumarthi\",\"pushkar.choudhary\",\"praveen.khare\",\"rajasulochana.komire\",\"naveen.kumashi\",\"showji.lambert\",\"anusuya.manohar\",\"anupriya.paul\",\"pratap.sahoo\",\"annapurna.sajja\",\"ajay.varghese\",\"shymol.varghese\",\"derek.bin.yang\",\"wen-jin.zhu\",\"yiwei.zhu\",\"ricardo.leon\",\"misha.rizkin\",\"jason.stenstrom\",\"mickye.stevenson\",\"larry.stuart\",\"yazhen\",\"sebastian.lammering\",\"pratik.shroff\",\"rupesh.kumar.porwal\",\"oscar.cornejo\"]";
					HttpRequestUtil.makeRequest(endpoint + path, data, HttpRequestType.POST, this);
					if(this.hasError())
					{
						Thread.sleep(10000);//10 seconds, a total of 5 min's
						logger.debug("waiting before retrying " + i);
						if(i == 30){// on the last iteration, log the error
							logger.error("Errors in make request, retries were unsuccessful", respexcept);
						}
					}else{
						break;
					}
				}catch(Exception e){
					logger.error("Exception caught while calling makeRequest " + endpoint + path, e);
				}
			}
			return response;
		}else{
			return null;
		}
			
	}
    public boolean hasError()
    {
    	return haserrors;
    }
    /**
     * @return - return exception
     */
    public Throwable getException()
    {
    	return respexcept;
    }
    public void Error(String request, Throwable exception)
    {
    	//special handling?
    	logger.debug(request);
    	logger.debug(exception.toString());
    	haserrors  = (exception!=null);
    	respexcept = exception;
    		
    }
    /**
	 * used by call back to populate response strings
	 */
	public void ResponseRecieved(String resp)
	{
		response = resp;
		// parse the string into gson object
		// perform any parse or generation of the object
	}
	public String getResponse()
	{
		return response;
	}
	public String toString()
	{
		return response;
	}

}