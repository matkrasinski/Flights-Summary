package pl.ksr.database;

public class Flight {

    private final int departureDay;
    private final int departureTime;
    private final float departureDelay;
    private final float airTime;
    private final float distance;
    private final int yearOfManufacture;
    private final float temperature;
    private final float windDir;
    private final float windSpeed;
    private final float windGust;
    private final float relativeHumidity;
    private final float lowestCloudLayer;

    private Flight(FlightBuilder builder) {
        this.departureDay = builder.departureDay;
        this.departureTime = builder.departureTime;
        this.departureDelay = builder.departureDelay;
        this.airTime = builder.airTime;
        this.distance = builder.distance;
        this.yearOfManufacture = builder.yearOfManufacture;
        this.temperature = builder.temperature;
        this.windDir = builder.windDir;
        this.windSpeed = builder.windSpeed;
        this.windGust = builder.windGust;
        this.relativeHumidity = builder.relativeHumidity;
        this.lowestCloudLayer = builder.lowestCloudLayer;
    }

    public static class FlightBuilder {
        private int departureDay;
        private int departureTime;
        private float departureDelay;
        private float airTime;
        private float distance;
        private int yearOfManufacture;
        private float temperature;
        private float windDir;
        private float windSpeed;
        private float windGust;
        private float relativeHumidity;
        private float lowestCloudLayer;

        public FlightBuilder departureDay(int departureDay) {
            this.departureDay = departureDay;
            return this;
        }
        public FlightBuilder departureTime(int departureTime) {
            this.departureTime = departureTime;
            return this;
        }
        public FlightBuilder departureDelay(float departureDelay) {
            this.departureDelay = departureDelay;
            return this;
        }
        public FlightBuilder airTime(float airTime) {
            this.airTime = airTime;
            return this;
        }
        public FlightBuilder distance(float distance) {
            this.distance = distance;
            return this;
        }
        public FlightBuilder yearOfManufacture(int yearOfManufacture) {
            this.yearOfManufacture = yearOfManufacture;
            return this;
        }
        public FlightBuilder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }
        public FlightBuilder windDir(float windDir) {
            this.windDir = windDir;
            return this;
        }
        public FlightBuilder windSpeed(float windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }
        public FlightBuilder windGust(float windGust) {
            this.windGust = windGust;
            return this;
        }
        public FlightBuilder relativeHumidity(float relativeHumidity) {
            this.relativeHumidity = relativeHumidity;
            return this;
        }
        public FlightBuilder lowestCloudLayer(float lowestCloudLayer) {
            this.lowestCloudLayer = lowestCloudLayer;
            return this;
        }
        public Flight build() {
            return new Flight(this);
        }

    }

    public int getDepartureDay() {
        return departureDay;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public float getDepartureDelay() {
        return departureDelay;
    }

    public float getAirTime() {
        return airTime;
    }

    public float getDistance() {
        return distance;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWindDir() {
        return windDir;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindGust() {
        return windGust;
    }

    public float getRelativeHumidity() {
        return relativeHumidity;
    }

    public float getLowestCloudLayer() {
        return lowestCloudLayer;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("departureDay", departureDay)
                .append("departureTime", departureTime)
                .append("departureDelay", departureDelay)
                .append("airTime", airTime)
                .append("distance", distance)
                .append("yearOfManufacture", yearOfManufacture)
                .append("temperature", temperature)
                .append("windDir", windDir)
                .append("windSpeed", windSpeed)
                .append("windGust", windGust)
                .append("relativeHumidity", relativeHumidity)
                .append("lowestCloudLayer", lowestCloudLayer)
                .toString();
    }
}

