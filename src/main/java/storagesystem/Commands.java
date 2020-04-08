package storagesystem;

enum Commands {

    ADD {
        @Override
        void perform(String productName, Integer productQuantity) {

            if (!ProcessData.getListOfProducts().containsKey(productName)) {
                ProcessData.getListOfProducts().put(productName, productQuantity);
            } else {
                ProcessData.getListOfProducts().compute(productName, (k, v) -> v + productQuantity);
            }

        }
    },
    REMOVE {
        @Override
        void perform(String productName, Integer productQuantity) {

            if (!ProcessData.getListOfProducts().containsKey(productName)) {
                System.out.println("No such product: " + productName);
            } else {
                if (ProcessData.getListOfProducts().get(productName) >= productQuantity) {
                    ProcessData.getListOfProducts().compute(productName, (k, v) -> v + productQuantity);
                } else {
                    System.out.println("Cannot do that - given quantity: " + productQuantity + " extends storage state: " + ProcessData.listOfProducts.get(productName));
                }
            }
        }
    },
    SET {
        @Override
        void perform(String productName, Integer productQuantity) {
            ProcessData.getListOfProducts().remove(productName);
            ProcessData.getListOfProducts().put(productName, productQuantity);
        }
    };

    abstract void perform(String productName, Integer productQuantity);
}

