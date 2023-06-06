------------------------
|     PreCrimeSystem    |
------------------------
| - crimeReports: List<CrimeReport>  |
| - predictionEngine: PredictionEngine |
------------------------
| + addCrimeReport(report: CrimeReport): void |
| + removeCrimeReport(report: CrimeReport): void |
| + getCrimeReports(): List<CrimeReport> |
| + analyzeCrimeReports(): void |
------------------------

------------------------
|     CrimeReport       |
------------------------
| - reportId: int              |
| - location: Location         |
| - crimeType: CrimeType       |
| - dateTime: DateTime         |
------------------------
| + getReportId(): int         |
| + getLocation(): Location    |
| + getCrimeType(): CrimeType  |
| + getDateTime(): DateTime    |
------------------------

------------------------
|      Location         |
------------------------
| - latitude: double          |
| - longitude: double         |
------------------------
| + getLatitude(): double    |
| + getLongitude(): double   |
------------------------

------------------------
|     CrimeType         |
------------------------
| - typeId: int               |
| - typeName: string          |
------------------------
| + getTypeId(): int         |
| + getTypeName(): string    |
------------------------

------------------------
|   PredictionEngine    |
------------------------
| - trainedModel: Model       |
------------------------
| + loadModel(model: Model): void |
| + predictCrime(report: CrimeReport): PredictionResult |
------------------------

------------------------
|   Model               |
------------------------
| - parameters: Map<String, Object>    |
| - weights: Map<String, Object>       |
------------------------
| + getParameters(): Map<String, Object> |
| + getWeights(): Map<String, Object>    |
------------------------

------------------------
|   PredictionResult    |
------------------------
| - report: CrimeReport        |
| - predictedOutcome: Outcome  |
------------------------
| + getReport(): CrimeReport    |
| + getPredictedOutcome(): Outcome |
------------------------

------------------------
|      Outcome          |
------------------------
| - outcomeId: int             |
| - outcomeName: string        |
------------------------
| + getOutcomeId(): int       |
| + getOutcomeName(): string  |
------------------------
