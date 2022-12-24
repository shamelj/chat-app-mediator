package org.mediator.project.mediator;

public class ChatMediatorFactory {

    public ChatMediator getChatMediator(String type){
        ChatMediator ans = null;

        if(type.trim().equalsIgnoreCase("single-chatroom")){
            ans = new SingleChatroomMediator();
        } else if (type.trim().equalsIgnoreCase("pairs")) {
            ans = new ChatPairsMediator();
        }

        return ans;
    }

}
