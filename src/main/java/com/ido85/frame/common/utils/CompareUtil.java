package com.ido85.frame.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  * Created by shhao  * Date: 14-5-6.  * Time:下午4:48  
 */
public class CompareUtil {

	/**
	 * sort 1正序 -1 倒序  filed 多字段排序
	 * 字符串排序时 ：按字典顺序比较两个字符串。该比较基于字符串中各个字符的 Unicode 值。
	 * @param sort
	 * @param filed
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Comparator createComparator(int sort, String... filed) {
		return new ImComparator(sort, filed);
	}

	@SuppressWarnings("rawtypes")
	public static class ImComparator implements Comparator {
		int sort = 1;
		String[] filed;
		public ImComparator(int sort, String... filed) {
			this.sort = sort == -1 ? -1 : 1;
			this.filed = filed;
		}

		@Override
		public int compare(Object o1, Object o2) {
			int result = 0;
			if(null!=this.filed&&this.filed.length>0){
				for (String file : filed) {
					
					Object value1 = ReflexUtil.invokeMethod(file, o1);
					Object value2 = ReflexUtil.invokeMethod(file, o2);
					if (value1 == null || value2 == null) {
						continue;
					}
					if (value1 instanceof Integer) { // Integer整数序排序
						int v1 = Integer.valueOf(value1.toString()).intValue();
						int v2 = Integer.valueOf(value2.toString()).intValue();
						if (v1 == v2)
							continue;
						if (sort == 1) {
							return v1 - v2;
						} else if (sort == -1) {
							return v2 - v1;
						}
					} else if (value1 instanceof Date) { // Date类型排序
						Date d1 = (Date) value1;
						Date d2 = (Date) value2;
						if (d1.compareTo(d2) == 0)
							continue;
						if (sort == 1) {
							return d1.compareTo(d2);
						} else if (sort == -1) {
							return d2.compareTo(d1);
						}
					} else if (value1 instanceof Long) { // Long排序
						long v1 = Long.valueOf(value1.toString());
						long v2 = Long.valueOf(value2.toString());
						if (v1 == v2)
							continue;
						if (sort == 1) {
							return v1 > v2 ? 1 : -1;
						} else if (sort == -1) {
							return v2 > v1 ? 1 : -1;
						}
					}else if (value1 instanceof Float) { // Float排序
						float v1 = Float.valueOf(value1.toString());
						float v2 = Float.valueOf(value2.toString());
						if (v1 == v2)
							continue;
						if (sort == 1) {
							return v1 > v2 ? 1 : -1;
						} else if (sort == -1) {
							return v2 > v1 ? 1 : -1;
						}
					} else if (value1 instanceof Double) { // Double排序
						Double v1 = Double.valueOf(value1.toString());
						Double v2 = Double.valueOf(value2.toString());
						if (v1.doubleValue() == v2.doubleValue())
							continue;
						if (sort == 1) {
							return v1 > v2 ? 1 : -1;
						} else if (sort == -1) {
							return v2 > v1 ? 1 : -1;
						}
					}else if (value1 instanceof String) { // String排序
						String v1 = String.valueOf(value1);
						String v2 = String.valueOf(value2);
						if (v1.equals(v2))
							continue;
						if (sort == 1) {
							return v1.compareTo(v2) > 0 ? 1 : -1;
						} else if (sort == -1) {
							return v2.compareTo(v1) > 0 ? 1 : -1;
						}
					}

				}
			}else {

				Object value1 = o1;
				Object value2 = o2;
				if (value1 == null || value2 == null) {
					return result;
				}
				if (value1 instanceof Integer) { // Integer整数序排序
					int v1 = Integer.valueOf(value1.toString()).intValue();
					int v2 = Integer.valueOf(value2.toString()).intValue();
					if (v1 == v2)
						return result;
					if (sort == 1) {
						return v1 - v2;
					} else if (sort == -1) {
						return v2 - v1;
					}
				} else if (value1 instanceof Date) { // Date类型排序
					Date d1 = (Date) value1;
					Date d2 = (Date) value2;
					if (d1.compareTo(d2) == 0)
						return result;
					if (sort == 1) {
						return d1.compareTo(d2);
					} else if (sort == -1) {
						return d2.compareTo(d1);
					}
				} else if (value1 instanceof Long) { // Long排序
					long v1 = Long.valueOf(value1.toString());
					long v2 = Long.valueOf(value2.toString());
					if (v1 == v2)
						return result;
					if (sort == 1) {
						return v1 > v2 ? 1 : -1;
					} else if (sort == -1) {
						return v2 > v1 ? 1 : -1;
					}
				}else if (value1 instanceof Float) { // Float排序
					float v1 = Float.valueOf(value1.toString());
					float v2 = Float.valueOf(value2.toString());
					if (v1 == v2)
						return result;
					if (sort == 1) {
						return v1 > v2 ? 1 : -1;
					} else if (sort == -1) {
						return v2 > v1 ? 1 : -1;
					}
				} else if (value1 instanceof Double) { // Double排序
					Double v1 = Double.valueOf(value1.toString());
					Double v2 = Double.valueOf(value2.toString());
					if (v1.doubleValue() == v2.doubleValue())
						return result;
					if (sort == 1) {
						return v1 > v2 ? 1 : -1;
					} else if (sort == -1) {
						return v2 > v1 ? 1 : -1;
					}
				}else if (value1 instanceof String) { // String排序
					String v1 = String.valueOf(value1);
					String v2 = String.valueOf(value2);
					if (v1.equals(v2))
						return result;
					if (sort == 1) {
						return v1.compareTo(v2) > 0 ? 1 : -1;
					} else if (sort == -1) {
						return v2.compareTo(v1) > 0 ? 1 : -1;
					}
				}

			
			}
			

			return result;
		}
	}
	
	
	/**
	 * sort 1正序 -1 倒序 
	 * tag 1 按绝对值排序  filed 多字段排序
	 * 字符串排序时 ：按字典顺序比较两个字符串。该比较基于字符串中各个字符的 Unicode 值。
	 * @param tag
	 * @param sort
	 * @param filed
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Comparator createAbsoluteComparator(Integer tag, int sort, String... filed) {
		return new ImComparatorAbsolute(tag, sort, filed);
	}
	
	@SuppressWarnings("rawtypes")
	public static class ImComparatorAbsolute implements Comparator {
		int sort = 1;
		String[] filed;
		int tag = 0;

		public ImComparatorAbsolute(Integer tag, int sort, String... filed) {
			this.sort = sort == -1 ? -1 : 1;
			this.filed = filed;
			this.tag = tag;
		}

		@Override
		public int compare(Object o1, Object o2) {
			int result = 0;
			for (String file : filed) {
				Object value1 = ReflexUtil.invokeMethod(file, o1);
				Object value2 = ReflexUtil.invokeMethod(file, o2);
				if (value1 == null || value2 == null) {
					continue;
				}
				if (value1 instanceof Integer) { // Integer整数序排序
					int v1 = Integer.valueOf(value1.toString()).intValue();
					int v2 = Integer.valueOf(value2.toString()).intValue();
					if(tag == 1){
						v1 = Math.abs(v1);
						v2 = Math.abs(v2);
					}
					if (v1 == v2)
						continue;
					if (sort == 1) {
						return v1 - v2;
					} else if (sort == -1) {
						return v2 - v1;
					}
				} else if (value1 instanceof Date) { // Date类型排序
					Date d1 = (Date) value1;
					Date d2 = (Date) value2;
					if (d1.compareTo(d2) == 0)
						continue;
					if (sort == 1) {
						return d1.compareTo(d2);
					} else if (sort == -1) {
						return d2.compareTo(d1);
					}
				} else if (value1 instanceof Long) { // Long排序
					long v1 = Long.valueOf(value1.toString());
					long v2 = Long.valueOf(value2.toString());
					if(tag == 1){
						v1 = Math.abs(v1);
						v2 = Math.abs(v2);
					}
					if (v1 == v2)
						continue;
					if (sort == 1) {
						return v1 > v2 ? 1 : -1;
					} else if (sort == -1) {
						return v2 > v1 ? 1 : -1;
					}
				}else if (value1 instanceof Float) { // Float排序
					float v1 = Float.valueOf(value1.toString());
					float v2 = Float.valueOf(value2.toString());
					if(tag == 1){
						v1 = Math.abs(v1);
						v2 = Math.abs(v2);
					}
					if (v1 == v2)
						continue;
					if (sort == 1) {
						return v1 > v2 ? 1 : -1;
					} else if (sort == -1) {
						return v2 > v1 ? 1 : -1;
					}
				} else if (value1 instanceof Double) { // Double排序
					Double v1 = Double.valueOf(value1.toString());
					Double v2 = Double.valueOf(value2.toString());
					if(tag == 1){
						v1 = Math.abs(v1);
						v2 = Math.abs(v2);
					}
					if (v1.doubleValue() == v2.doubleValue())
						continue;
					if (sort == 1) {
						return v1 > v2 ? 1 : -1;
					} else if (sort == -1) {
						return v2 > v1 ? 1 : -1;
					}
				}else if (value1 instanceof String) { // String排序
					String v1 = String.valueOf(value1);
					String v2 = String.valueOf(value2);
					if (v1.equals(v2))
						continue;
					if (sort == 1) {
						return v1.compareTo(v2) > 0 ? 1 : -1;
					} else if (sort == -1) {
						return v2.compareTo(v1) > 0 ? 1 : -1;
					}
				}

			}

			return result;
		}
	}

	/*public static void main(String args[]) {
		Video label1 = new Video();
		label1.setDisplayOrder(3);
		label1.setMoney(20.0);
		label1.setViplevel("3");
		label1.setCreateTime(StrToDate("2014-01-01 12:12:12"));
		
		Video label2 = new Video();
		label2.setDisplayOrder(3);
		label2.setMoney(20.0);
		label2.setViplevel("1");
		label2.setCreateTime(StrToDate("2014-01-01 12:12:12"));
		
		Video label3 = new Video();
		label3.setDisplayOrder(2);
		label3.setCreateTime(StrToDate("2014-01-01 12:12:12"));
		label3.setMoney(10.0);
		label3.setViplevel("2");
		
		Video label4 = new Video();
		label4.setDisplayOrder(2);
		label4.setCreateTime(StrToDate("2014-01-01 21:00:00"));
		label4.setMoney(30.0);
		label4.setViplevel("2");
		
		Video label5 = new Video();
		label5.setDisplayOrder(4);
		label5.setCreateTime(StrToDate("2014-01-05 15:00:00"));
		label5.setMoney(40.0);
		label5.setViplevel("4");
		
		List<Video> list = new ArrayList<Video>();
		list.add(label1);
		list.add(label2);
		list.add(label3);
	    list.add(label4);
        list.add(label5);
		Collections.sort(list,
				CompareUtil.createComparator(-1, "viplevel"));
		for (int i = 0; i < list.size(); i++) {
			Video labelAlbum = list.get(i);
			System.out.println("displayorder:" + labelAlbum.getDisplayOrder()
					+ "  sequence："
					+ labelAlbum.getCreateTime().toLocaleString()+" money:"+labelAlbum.getMoney()+" vipLevel:"+labelAlbum.getViplevel());
		}
	}*/
	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
}
