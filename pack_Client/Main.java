package terminal;

import graphisme.*;

public class Main {
    public static void main(String[] args) {
        try {
            ClientInteraction client = new ClientInteraction();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }
}
