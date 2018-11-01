import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;

class Warehouse {
    private List<Package> warehouseInventory = new ArrayList<>();

    /**
     * Adds a package to the 'warehouse'
     *
     * @param p Package to be added
     */
    void addPackage(Package p) {
        warehouseInventory.add(p);
    }

    /**
     * Process each package based on the date and 'delivers' accordingly
     *
     * @param dateOfDelivery date specified for distribution.
     */
    void startDelivery(LocalDate dateOfDelivery) {
        CountDownLatch latch; // number of Couriers to be dispatched(threads)
        List<Package> sortedByDate = sortByDate(dateOfDelivery);
        Map<String, List<Package>> packagesToDeliver = new HashMap<>(
                sortByDestination(sortedByDate));

        if (packagesToDeliver.size() > 0) {
            //'latch' equals total unique deliveries(threads to be started)
            latch = new CountDownLatch(packagesToDeliver.size());

            System.out.println();
            System.out.println("Commenced the delivery on " + sortedByDate.get(0).getDeliveryDate());
            System.out.println();

            for (String location : packagesToDeliver.keySet()) {
                // gets the first item from the list, with all the packages for one particular location.
                // the list cannot be ever empty. Contains at least one item
                Package p = packagesToDeliver.get(location).get(0);

                //dispatch the courier for that location
                Courier courier = new Courier(packagesToDeliver.get(location), p.getTargetLocation(), p.getDistanceToTarget(), latch);
                Thread deliveryService = new Thread(courier);
                deliveryService.start();

                // remove from the warehouse inventory the dispatched items for each location
                removeFromInventory(packagesToDeliver.get(location));
            }
            // waits for all threads(deliveries) to be completed, so the total profit and values can be computed
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
            displayProfit(sortedByDate);
            displayTotalValue(sortedByDate);
        } else {
            System.out.println("\nNo packages to deliver for " + dateOfDelivery);
        }

    }

    /**
     * Get the packages by the specified date only
     *
     * @param date date to compare the packages with
     * @return a list with all the packages from a specified date
     */
    private List<Package> sortByDate(LocalDate date) {
        List<Package> sortedByDate = new ArrayList<>();
        for (Package p : warehouseInventory) {
            if (p.getDeliveryDate().equals(date)) {
                sortedByDate.add(p);
            }
        }
        return sortedByDate;
    }

    /**
     * Sorts the packages by destination
     *
     * @param sortedByDate list contained packages for a particular date
     * @return a map containing packages sorted by unique destination(key is the destination,
     * value is the list with the corresponding packages
     */
    private Map<String, List<Package>> sortByDestination(List<Package> sortedByDate) {
        Set<String> uniqueDestinations = new HashSet<>();
        Map<String, List<Package>> sortedByDestination = new HashMap<>();
        for (Package p : sortedByDate) {
            uniqueDestinations.add(p.getTargetLocation()); // adds each location entry. Duplicates are ignored.
        }

        for (String destination : uniqueDestinations) {
            List<Package> tempList = new ArrayList<>();
            for (Package p : sortedByDate) {
                if (p.getTargetLocation().equalsIgnoreCase(destination)) {
                    tempList.add(p);
                }
            }
            // puts into the map the destination as a key and the list of corresponding packages
            sortedByDestination.put(destination, tempList);
        }
        return sortedByDestination;
    }

    /**
     * Removes the 'delivered' packages from the warehouse
     *
     * @param deliveredItems list with the items to be removed
     */
    private void removeFromInventory(List<Package> deliveredItems) {
        warehouseInventory.removeAll(deliveredItems);
    }

    /**
     * Calculates  and displays 'the entire profit' for all the packages in the list
     *
     * @param packagesToDeliver list of packages to calculate the profit from
     */
    private void displayProfit(List<Package> packagesToDeliver) {
        double profit = 0;
        double fixValue = 1.2;
        String currency = " RON";
        LocalDate date = packagesToDeliver.get(0).getDeliveryDate();
        for (Package p : packagesToDeliver) {
            profit = profit + (p.getDistanceToTarget() * fixValue);
        }
        String profitFormatted = String.format("%.1f",profit);
        System.out.println("The total profit made from deliveries on: " + date + " is: " + profitFormatted + currency);
    }

    /**
     * Calculates  and displays 'the total values' of all the packages in the list
     *
     * @param packagesToDeliver list of packages to calculate the values from
     */
    private void displayTotalValue(List<Package> packagesToDeliver) {
        double totalValue = 0;
        String currency = " RON";
        LocalDate date = packagesToDeliver.get(0).getDeliveryDate();
        for (Package p : packagesToDeliver) {
            totalValue = totalValue + p.getValue();
        }
        String totalValueFormatted = String.format("%.1f",totalValue);
        System.out.println("The total value of the packages delivered on: " + date + " is: " + totalValueFormatted + currency);
    }
}
