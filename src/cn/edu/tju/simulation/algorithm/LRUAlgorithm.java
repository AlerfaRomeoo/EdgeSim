package cn.edu.tju.simulation.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * �������ʹ��
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class LRUAlgorithm implements RealTimeAlgorithm{

	/**
	 * �����������µ�
	 * @param network
	 * @param requestContent
	 */
	public void setCache(WirelessNetwork network,SingleLocalHobby requestContent){
		long remainingSize = network.getRemainingCacheSize();
		LinkedList<CachingSingleContent> cachingContentList =  network.getCacheContent();
		CachingSingleContent includeContent = include(cachingContentList, requestContent);

		
//			System.out.println("��������ݣ�"+requestContent.getName());
			if(includeContent != null){
				//���������ڰ���������ݣ������ŵ���ǰ��
//				System.out.println("�������Ѿ����ڣ��滻����ǰ��");
				cachingContentList.remove(includeContent);
				cachingContentList.addFirst(includeContent);
			}else{
				if(remainingSize >= requestContent.getSize()){
					//��װ��
//					System.out.println("��������װ�£�ֱ�ӻ���");
					cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
				}else{
//					System.out.println("ʣ���С��"+remainingSize +" ����Ĵ�С:"+ network.getCacheSize()*(float)(1f/6f));
					//װ���£�һ��һ�������ӣ�ֱ��װ��Ϊֹ
					if(network.getCacheSize() >= requestContent.getSize()){
//						System.out.println("װ���£�һ��һ�������ӣ�ֱ��װ��Ϊֹ");
						long tempRenmaingSize = remainingSize;
						LinkedList<CachingSingleContent> tempList = new LinkedList<CachingSingleContent>();
						for (int i = cachingContentList.size() - 1; i >= 0; i--) {	
							tempRenmaingSize += cachingContentList.get(i).getSize();
//							System.out.println("ʣ���С  "+tempRenmaingSize);
//							System.out.println("���ݴ�С " + requestContent.getSize());
							tempList.add(network.getCacheContent().get(i));
							if (tempRenmaingSize >= requestContent.getSize()) {
								break;
							}
						}
						
						int sumOfPopularity = 0;
						for(int j = 0 ; j <tempList.size();j++){
							SingleLocalHobby mySingleContentInNetwork = network.getContent().getSingleLocalHobbyBySingleContent(tempList.get(j).getSingleContent());
							sumOfPopularity += mySingleContentInNetwork.getLocalHobbyValue();							
						}
						SingleLocalHobby mySingleContentInNetwork = network.getContent().getSingleLocalHobbyBySingleContent(requestContent.getSingleContent());
						if(sumOfPopularity < mySingleContentInNetwork.getLocalHobbyValue() ){
							network.getCacheContent().removeAll(tempList);
						}
				}

			}
		
		
			}
			Controller.getInstance().appendLog("debug","Update Cache", null);
	}
	
	public CachingSingleContent include(List<CachingSingleContent> cacheContent , SingleLocalHobby content){
		Iterator<CachingSingleContent> it = cacheContent.iterator();
		while(it.hasNext()){
			CachingSingleContent csc = it.next();
			if(csc.getName().equals(content.getName())){
				System.out.println(cacheContent.contains(content));
				return csc;
			}
		}
		return null;
	}
}
