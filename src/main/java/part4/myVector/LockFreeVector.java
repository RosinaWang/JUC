package part4.myVector;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by Dell on 2017-08-02.
 */
public class LockFreeVector<E>{
    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
    private final int N_BUCKET=30;
    private final int FIRST_BUCKET_SIZE=8;
    private AtomicReference<Descriptor<E>> descriptor;
    public LockFreeVector(){
        buckets=new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
        buckets.set(0,new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
        descriptor=new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null));
    }
//    public void push_back(E e){
//        Descriptor<E> desc;
//        Descriptor<E> newd;
//        do {
//            desc=descriptor.get();
//            desc.completeWrite();
//
//            int pos=desc.size+FIRST_BUCKET_SIZE;
//            int zeroNumPos=Integer.numberOfLeadingZeros(pos);
//            int bucketInd=zeroNumFirst-zeroNumPos;
//            if(buckets.get(bucketInd)==null){
//                int newLen=2*buckets.get(bucketInd-1).length();
//                if(debug)
//                    System.out.println("New Length is :"+newLen);
//                buckets.compareAndSet(bucketInd,null,new AtomicReferenceArray<E>(newLen));
//            }
//            int idx=(0x80000000>>>zeroNumPos)^pos;
//            newd=new Descriptor<E>(desc.size+1,new WriteDescriptor<E>(buckets.get(bucketInd), idx,null,e));
//        }while (!descriptor.compareAndSet(desc,newd));
//        descriptor.get().completeWrite();
//    }












    static class Descriptor<E>{//为了更有序的读写数组
        public int size;
        volatile WriteDescriptor<E> writeop;

        public Descriptor(int size, WriteDescriptor<E> writeop) {
            this.size = size;//Vector长度
            this.writeop = writeop;
        }
        public void completeWrite(){//通过这个方法写入数据
            WriteDescriptor<E> tmpOp=writeop;
            if(tmpOp!=null){
                tmpOp.doIt();
                writeop=null;
            }
        }
    }
    static class WriteDescriptor<E>{//封装了一次操作
        public E oldV;
        public E newV;
        public AtomicReferenceArray<E> addr;
        public int addr_ind;

        public WriteDescriptor( AtomicReferenceArray<E> addr, int addr_ind,E oldV, E newV) {
            this.oldV = oldV;//期望
            this.newV = newV;//写入的新值
            this.addr = addr;//代表要修改的数组
            this.addr_ind = addr_ind;//要写入的数组索引位置
        }

        public void doIt() {
            addr.compareAndSet(addr_ind,oldV,newV);
        }
    }

}
