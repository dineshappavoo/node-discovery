/**
 * 
 */
package com.node.discovery.sctp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.node.discovery.graph.Host;

/**
 * @author Dinesh Appavoo
 *
 */
public class SctpClientServer{

	private int noOfVertices;
	private static HashMap<Integer, Host> nodeMap;

	public void initiateThread()
	{
		SctpClient  sctpClient = new SctpClient();
		SctpServer sctpServer = new SctpServer();

		new Thread(sctpClient).start();
		new Thread(sctpServer).start();
	}

	public HashMap<Integer, Host> constructGraph(String fileName, int nodeId) throws FileNotFoundException
	{
		nodeMap = new HashMap<Integer, Host>();
		File file = new File(fileName);
		Scanner scanner=new Scanner(file);
		int noOfEdges;
		int u, v;
		int hostId, hostPort;
		String hostName="";
		String checker="";

		while(scanner.hasNext())
		{
			if((checker=scanner.next()).equals("p") && (!(checker.equals("#"))))
			{
				noOfVertices=scanner.nextInt();
				noOfEdges=scanner.nextInt();
				for(int i=0;i<noOfEdges;i++)
				{
					if((checker=scanner.next()).equals("e"))
					{
						u=scanner.nextInt();
						v=scanner.nextInt();
						//System.out.println("u: "+u+" v : "+v);
						if (u == nodeId)
						{
							//System.out.println("u: "+u+" v : "+v);
							nodeMap.put(v, new Host());
						}
					}
				}

				//System.out.println(nodeMap.toString());
				//To add the node information from config file [FORMAT : n  0	dc01		3332]
				for(int j=0;j<noOfVertices;j++)
				{
					if((checker=scanner.next()).equals("n")){
						hostId = scanner.nextInt();
						hostName = scanner.next();
						hostPort = scanner.nextInt();

						if(nodeMap.get(hostId)!=null)
						{
							nodeMap.put(hostId, new Host(hostId, hostName, hostPort));
						}
					}
				}
			}
		}
		return nodeMap;
	}

	public void  startDiscovery(ArrayList<Host> hostList)
	{
		int size = hostList.size();
		int nNumOfThreads=size;
		Thread[] tThreads = new Thread[nNumOfThreads];
		SctpClient  sctpClient;
		for(int i=0;i<size;i++)
		{
			if(nodeMap.get(hostList.get(i).hostId) ==null)
			{
				sctpClient = new SctpClient();
				tThreads[i] = new Thread(sctpClient);
				tThreads[i].start();
			}
		}
	}


	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		SctpClientServer sctpClientServer = new SctpClientServer();
		HashMap<Integer, Host> nMap = sctpClientServer.constructGraph("/Users/Dany/Documents/FALL-2013-COURSES/Imp_Data_structures/workspace/node-discovery/src/com/node/discovery/input/config.txt", 1);
		ArrayList<Host> tempNodeList = new ArrayList<Host>();
		for(int node : nMap.keySet())
		{
			Host host = nMap.get(node);
			if(host!=null)
			{
				tempNodeList.add(host);
				System.out.println("NodeId : "+host.hostId+" Host Name : "+host.hostName+" Port : "+host.hostPort);
			}
		}
	}

}
