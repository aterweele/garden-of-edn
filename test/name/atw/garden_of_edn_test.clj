(ns name.atw.garden-of-edn-test
  (:require [clojure.test :refer [deftest testing is]]
            [name.atw.garden-of-edn :as garden])
  (:import java.lang.management.ManagementFactory))

(deftest resource
  (let [fds-before (.getOpenFileDescriptorCount
                    (ManagementFactory/getOperatingSystemMXBean))]
    (is (= {:id #uuid "3b10bf99-58f1-4849-95c6-7839c5f2c2fc"}
           (garden/resource "test.edn")))
    (is (= fds-before (.getOpenFileDescriptorCount
                       (ManagementFactory/getOperatingSystemMXBean)))))
  (testing "It is possible to specify opts for clojure.edn/read."
    (is (= {:id "3b10bf99-58f1-4849-95c6-7839c5f2c2fc"}
           (garden/resource {:readers (assoc default-data-readers
                                             'uuid identity)}
                            "test.edn")))))
