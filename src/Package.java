import java.time.LocalDate;

class Package {
    private String targetLocation;
    private int distanceToTarget;
    private int value;
    private LocalDate deliveryDate;

    Package(String targetLocation, int distanceToTarget, int value, LocalDate deliveryDate) {
        this.targetLocation = targetLocation;
        this.distanceToTarget = distanceToTarget;
        this.value = value;
        this.deliveryDate = deliveryDate;
    }

    String getTargetLocation() {
        return targetLocation;
    }


    int getDistanceToTarget() {
        return distanceToTarget;
    }


    int getValue() {
        return value;
    }

    LocalDate getDeliveryDate() {
        return deliveryDate;
    }

}
