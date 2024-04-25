# Flights-Summary

Repository is dedicated to university course "Computer Recognition Systems".

This JDK-based application provides access to PostgreSQL for linguistically aggregating data from the "2022 US Airlines Domestic Departure Data" dataset. With 15354 records capturing domestic flights in the USA during 2022, each record comprises 12 columns representing numerical flight attributes. From these, 12 linguistic variables have been derived. The application generates quasi-natural language summaries in English, simplifying past flight information for airlines and users interested in US domestic flights.

Experimental features include generating single-entity and multi-entity linguistic summaries, evaluating them using quality measures, interpreting results, and drawing conclusions.

Dataset is available here: https://www.kaggle.com/datasets/jl8771/2022-us-airlines-domestic-departure-data

### Few examples of linguistic summarizations:

1. almost none flights by Airbus that had/were wind from east compared
to flights by Boeing had/were light wind, 1.0

2. about half of flights by Boeing compared to flights by Airbus had/were
light wind, 1.0

3. little less than half of flights by Airbus that had/were light wind
compared to flights by Boeing had/were wind from east, 0.94

4. little less than half of flights by Boeing that had/were light wind
compared to flights by Airbus had/were wind from east, 0.84

5. little less than half of flights by Airbus compared to flights by Boeing
had/were light wind and had/were wind from east, 0.48

Each summarization is accompanied by a degree of truth measure, indicating the accuracy of the statement.
