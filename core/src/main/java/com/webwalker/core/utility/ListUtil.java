package com.webwalker.core.utility;

import com.webwalker.core.config.ConfigResolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xujian on 2018/7/8.
 */
public class ListUtil {
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

    /**
     * [简述]： 对List数组排序
     *
     * @param list 源数据 排序集合
     * @param sort 升序 还是 降序，默认升序
     * @return List
     */
    public static List<?> sort(List<?> list, final String sort) {
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                int ret = 0;
                if (o1 instanceof Integer) {
                    ret = ((Integer) o1).compareTo((Integer) o2);
                } else if (o1 instanceof Double) {
                    ret = ((Double) o1).compareTo((Double) o2);
                } else if (o1 instanceof Long) {
                    ret = ((Long) o1).compareTo((Long) o2);
                } else if (o1 instanceof Float) {
                    ret = ((Float) o1).compareTo((Float) o2);
                } else if (o1 instanceof Date) {
                    ret = ((Date) o1).compareTo((Date) o2);
                } else if (isDouble(String.valueOf(o1)) && isDouble(String.valueOf(o2))) {
                    ret = (new Double(o1.toString())).compareTo(new Double(o2.toString()));
                } else {
                    ret = String.valueOf(o1).compareTo(String.valueOf(o2));
                }
                if (null != sort && SORT_DESC.equalsIgnoreCase(sort)) {
                    return -ret;
                } else {
                    return ret;
                }
            }
        });
        return list;
    }

    /**
     * [简述]: List 泛型 排序
     *
     * @param list  源数据 排序集合
     * @param field 排序的数据字段名称
     * @param sort  升序 还是 降序，默认升序
     * @param <T>   泛型T
     * @return List
     */
    public static <T> List<T> sort(List<T> list, final String field, final String sort) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                int ret = 0;
                try {
                    Field field1 = o1.getClass().getDeclaredField(field);
                    field1.setAccessible(true);
                    Class<?> type = field1.getType();
                    if (type == int.class) {
                        ret = ((Integer) field1.getInt(o1)).compareTo((Integer) field1.getInt(o2));
                    } else if (type == double.class) {
                        ret = ((Double) field1.getDouble(o1)).compareTo((Double) field1.getDouble(o2));
                    } else if (type == long.class) {
                        ret = ((Long) field1.getLong(o1)).compareTo((Long) field1.getLong(o2));
                    } else if (type == float.class) {
                        ret = ((Float) field1.getFloat(o1)).compareTo((Float) field1.getFloat(o2));
                    } else if (type == Date.class) {
                        ret = ((Date) field1.get(o1)).compareTo((Date) field1.get(o2));
                    } else {
                        ret = String.valueOf(field1.get(o1)).compareTo(String.valueOf(field1.get(o2)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                if (null != sort && SORT_DESC.equalsIgnoreCase(sort)) {
                    return -ret;
                } else {
                    return ret;
                }
            }
        });
        return list;
    }

    private static boolean isDouble(String str) {
        boolean flag = false;
        if (isInteger(str) || isFloat(str)) {
            flag = true;
        }
        return flag;
    }

    private static boolean isInteger(String str) {
        Matcher matcher = Pattern.compile("^[+-]?[0-9]+$").matcher(str);
        return matcher.find();
    }

    private static boolean isFloat(String str) {
        return str.matches("[\\d]+\\.[\\d]+");
    }

    /**
     * [简述]: List 泛型 排序
     *
     * @param list   源数据 排序集合
     * @param fields 排序的数据字段名称
     * @param sorts  升序 还是 降序
     * @param <T>    泛型T
     * @return List
     */
    public static <T> List<T> sort(List<T> list, final String[] fields, final String[] sorts) {
        if (null != fields && fields.length > 0) {
            for (int index = 0; index < fields.length; index++) {
                String sortRule = SORT_ASC;
                if (null != sorts && sorts.length >= index && null != sorts[index]) {
                    sortRule = sorts[index];
                }
                final String sort = sortRule;
                final String field = fields[index];
                Collections.sort(list, new Comparator<T>() {
                    @Override
                    public int compare(T o1, T o2) {
                        int ret = 0;
                        try {
                            Field field1 = o1.getClass().getDeclaredField(field);
                            field1.setAccessible(true);
                            Class<?> type = field1.getType();
                            if (type == int.class) {
                                ret = ((Integer) field1.getInt(o1)).compareTo((Integer) field1.getInt(o2));
                            } else if (type == double.class) {
                                ret = ((Double) field1.getDouble(o1)).compareTo((Double) field1.getDouble(o2));
                            } else if (type == long.class) {
                                ret = ((Long) field1.getLong(o1)).compareTo((Long) field1.getLong(o2));
                            } else if (type == float.class) {
                                ret = ((Float) field1.getFloat(o1)).compareTo((Float) field1.getFloat(o2));
                            } else if (type == Date.class) {
                                ret = ((Date) field1.get(o1)).compareTo((Date) field1.get(o2));
                            } else {
                                ret = String.valueOf(field1.get(o1)).compareTo(String.valueOf(field1.get(o2)));
                            }


                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        if (null != sort && SORT_DESC.equalsIgnoreCase(sort)) {
                            return -ret;
                        } else {
                            return ret;
                        }
                    }
                });
            }
        }
        return list;
    }

    private static String getMethodName(String str) {
        StringBuffer name = new StringBuffer();
        name = name.append("get").append(firstLetterToCapture(str));
        return name.toString();
    }

    private static String firstLetterToCapture(String name) {
        char[] arr = name.toCharArray();
        arr[0] -= 32;
        return String.valueOf(arr);
    }

    //排序、获取指定数量、计算某个字段平均值
    public static <T> double sortAndAvg(List<T> list, String field, String sort, int size) {
        List<T> sortedList = sort(list, field, sort);
        List<T> limitList = getLimitList(sortedList, size);
        double price = computeAvg(limitList, field);
        return Utils.getDouble(price);
    }

    //获取指定数量的list item
    public static <T> List<T> getLimitList(List<T> list, int size) {
        int length = list.size();
        if (length <= size) {
            return list;
        }
        return list.subList(0, size - 1);
    }

    //计算平均值
    public static <T> double computeAvg(List<T> list, final String fieldName) {
        double ret = 0;
        for (T t : list) {
            try {
                Field field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (type == int.class) {
                    ret += field.getInt(t);
                } else if (type == double.class) {
                    ret += field.getDouble(t);
                } else if (type == long.class) {
                    ret += field.getLong(t);
                } else if (type == float.class) {
                    ret += field.getFloat(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ret != 0) {
            return ret / list.size();
        }
        return 0;
    }
}
