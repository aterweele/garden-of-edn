(ns name.atw.garden-of-edn-test
  (:require [clojure.test :refer [deftest testing is]]
            [name.atw.garden-of-edn :as garden])
  (:import java.lang.management.ManagementFactory))

(deftest the-test
  (doseq [{:keys [what f arg]}
          [{:what "resource", :f garden/resource, :arg "test.edn"}
           {:what "file", :f garden/file, :arg "test/test.edn"}]]
    (testing what
      (let [fds-before (.getOpenFileDescriptorCount
                        (ManagementFactory/getOperatingSystemMXBean))]
        (is (= {:id #uuid "3b10bf99-58f1-4849-95c6-7839c5f2c2fc"}
               (f arg)))
        (is (= fds-before (.getOpenFileDescriptorCount
                           (ManagementFactory/getOperatingSystemMXBean)))
            "No file descriptors may be left open."))
      (testing "It is possible to specify opts for clojure.edn/read."
        (is (= {:id "3b10bf99-58f1-4849-95c6-7839c5f2c2fc"}
               (f {:readers (assoc default-data-readers 'uuid identity)}
                  arg)))))))
