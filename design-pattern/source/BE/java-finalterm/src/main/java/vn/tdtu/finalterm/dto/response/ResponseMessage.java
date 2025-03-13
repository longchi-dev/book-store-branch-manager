package vn.tdtu.finalterm.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage {

//	private ControllerObservable controllerObservable =  new ControllerObservable();

    private Object message;
    private int status;
}
