package pl.ksr.summary;

public class Summary {

    private final String summary;
    private final QualityMeasures qualityMeasures;

    public Summary(String summary) {
        this.summary = summary;
        this.qualityMeasures = new QualityMeasures();
    }

    public Summary(String summary, QualityMeasures qualityMeasures) {
        this.summary = summary;
        this.qualityMeasures = qualityMeasures;
    }

    public String getSummary() {
        return summary;
    }

    public QualityMeasures getQualityMeasures() {
        return qualityMeasures;
    }
}
