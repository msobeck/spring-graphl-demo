package ms.graphl.demo.web;

import jakarta.validation.constraints.Max;

record ReviewInput(String commentary, @Max(5) int stars) {

}
