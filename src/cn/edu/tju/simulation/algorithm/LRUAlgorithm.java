package cn.edu.tju.simulation.algorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.MySingleContent;
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
	public void setCache(WirelessNetwork network,MySingleContent requestContent){
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
							MySingleContent mySingleContentInNetwork = network.getContent().getMySingleContentByInitialSingleContent(tempList.get(j).getCachingSingleContent());
							sumOfPopularity += mySingleContentInNetwork.getMyPopularity();							
						}
						MySingleContent mySingleContentInNetwork = network.getContent().getMySingleContentByInitialSingleContent(requestContent.getSingleContent());
						if(sumOfPopularity < mySingleContentInNetwork.getMyPopularity() ){
							network.getCacheContent().removeAll(tempList);
						}
				}

			}
		
		
			}

		
		
//	System.out.println("");
//			
//			for(int i = 0 ; i<cachingContentList.size();i++){
//				System.out.print(cachingContentList.get(i).getName()+" ");
//			}
//			
			Controller.getInstance().appendLog("debug","Update Cache", null);

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		long remainingSize = network.getRemainingCacheSize();
//		LinkedList<CachingSingleContent> cachingContentList =  network.getCacheContent();
//		CachingSingleContent includeContent = include(cachingContentList, requestContent);
//		if (requestContent.getSize()< 20480){
//			System.out.println("��������ݣ�"+requestContent.getName());
//			if(includeContent != null){
//				//���������ڰ���������ݣ������ŵ���ǰ��
//				System.out.println("�������Ѿ����ڣ��滻����ǰ��");
//				cachingContentList.remove(includeContent);
//				cachingContentList.addFirst(includeContent);
//			}else{
//				if(remainingSize >= requestContent.getSize()){
//					//��װ��
//					System.out.println("��������װ�£�ֱ�ӻ���");
//
//					cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
//				}else{
//					//װ���£�һ��һ�������ӣ�ֱ��װ��Ϊֹ
//					if(network.getCacheSize() >= requestContent.getSize()){
//						System.out.println("װ���£�һ��һ�������ӣ�ֱ��װ��Ϊֹ");
//						for (int i = cachingContentList.size() - 1; i >= 0; i--) {
//							remainingSize += cachingContentList.get(i).getSize();
//							System.out.println("ʣ���С  "+remainingSize);
//							System.out.println("���ݴ�С " + requestContent.getSize());
//							if (network.removeCacheContent(i)) {
//								if (remainingSize >= requestContent.getSize()) {
//									cachingContentList.addFirst(new CachingSingleContent(requestContent.getSingleContent()));
//									break;
//								}
//							}
//						}
//					}
//				}
//
//			}
//			
//			for(int i = 0 ; i<cachingContentList.size();i++){
//				System.out.print(cachingContentList.get(i).getName()+" ");
//			}
//			
//			Controller.getInstance().appendLog("debug","Update Cache", null);
//
//			System.out.println("�������ã���");
//		}
	}
	
	public CachingSingleContent include(List<CachingSingleContent> cacheContent , MySingleContent content){
		Iterator<CachingSingleContent> it = cacheContent.iterator();
		while(it.hasNext()){
			CachingSingleContent csc = it.next();
			if(csc.getName().equals(content.getName())){
				return csc;
			}
		}
		return null;
	}
}
