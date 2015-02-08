/**
 * 
 */
package com.node.discovery.graph;

/**
 * @author Dany
 *
 */
public class Host {
	
	int hostId;
	String hostName;
	int hostPort;
	
	public Host(int hostId, String hostName, int hostPort)
	{
		this.hostId = hostId;
		this.hostName = hostName;
		this.hostPort = hostPort;
	}
}
