/**
 * 
 */
package com.node.discovery.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Dany
 *
 */
public class NetworkConstruction {


	public static int noOfEdges;
	public static int noOfVertices;
	public static HashMap<Integer, Host> hostMap = new HashMap<Integer, Host>();
	public static Graph nodeGraph;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws FileNotFoundException, UnknownHostException {
		// TODO Auto-generated method stub

		new NetworkConstruction().constructGraph("/Users/Dany/Documents/FALL-2013-COURSES/Imp_Data_structures/workspace/node-discovery/src/com/node/discovery/input/config.txt");
		
		System.out.println(System.getProperty("user.name")); 
		java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
		System.out.println("Hostname of local machine: " + localMachine.getHostName());	}


	public void constructGraph(String fileName) throws FileNotFoundException
	{
		File file = new File(fileName);
		int u, v, w;
		Scanner scanner=new Scanner(file);
		int hostId, hostPort;
		String hostName="";
		String checker="";

		while(scanner.hasNext())
		{
			if((checker=scanner.next()).equals("p") && (!(checker.equals("#"))))
			{
				noOfVertices=scanner.nextInt();
				noOfEdges=scanner.nextInt();

				//System.out.println("edge "+noOfEdges+" vertex "+noOfVertices);
				nodeGraph=new Graph(noOfVertices);
				for(int i=0;i<noOfEdges;i++)
				{
					if((checker=scanner.next()).equals("e"))
					{
						u=scanner.nextInt();
						v=scanner.nextInt();
						//System.out.println("u: "+u+" v : "+v);
						nodeGraph.addEdge(u, v);
					}
				}

				//To add the node information from config file [FORMAT : n  0	dc01		3332]
				for(int j=0;j<noOfVertices;j++)
				{
					if((checker=scanner.next()).equals("n")){
						hostId = scanner.nextInt();
						hostName = scanner.next();
						hostPort = scanner.nextInt();
						//System.out.println("Host ID : "+hostId+" Host Name : "+hostName+" Host Port :  "+hostPort);
						hostMap.put(hostId, new Host(hostId, hostName, hostPort));
					}
				}
			}
		}
		printHostInfo();
		nodeGraph.printGraph();	
	}

	public void printHostInfo()
	{
		for(int nodeId : hostMap.keySet())
		{
			Host host = hostMap.get(nodeId);
			System.out.println("Host ID : "+host.hostId+" Host Name : "+host.hostName+" Host Port :  "+host.hostPort);
		}
	}

}
