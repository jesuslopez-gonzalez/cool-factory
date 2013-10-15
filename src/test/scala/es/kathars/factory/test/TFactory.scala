/*
 * Copyright (c) 2013 Jesús López-González
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.kathars.factory.test

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import es.kathars.factory._
import Factory.factory

class TFactory extends FunSpec with ShouldMatchers {

  @factory trait A { 
    val a1: Int
  }

  @factory trait B extends A {
  	val b1: String
  	val b2: List[Int]
  }

  describe("Factory") {

    it("should create a valid apply method") {
      A(1)             // ok
      B(2, "", List()) // this is what we want, but we need to know A attributes 
    }
  }
}
