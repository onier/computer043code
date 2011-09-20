/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ReferenceQueue referenceQueue = new ReferenceQueue();
        Object object = new Object() {

            public String toString() {
                return "Referenced Object";
            }
        };

        Object data = new Object() {

            public String toString() {
                return "Data";
            }
        };

        HashMap map = new HashMap();
        Reference reference = new SoftReference(object, referenceQueue);

        map.put(reference, data);

        System.out.println(reference.get());
        System.out.println(map.get(reference));
        System.out.println(reference.isEnqueued());

        System.gc();
        System.out.println(reference.get());
        System.out.println(map.get(reference));
        System.out.println(reference.isEnqueued());

        object = null;
        data = null;

        System.gc();
        System.out.println(reference.get());
        System.out.println(map.get(reference));
        System.out.println(reference.isEnqueued());
    }
}
