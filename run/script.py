import pandas as pd

# Columns from original dataset that will be included
columns = ['DEP_TIME',
           'DEP_DELAY',
           'AIR_TIME',
           'DISTANCE',
           'YEAR OF MANUFACTURE',
           'WIND_DIR',
           'WIND_SPD',
           'WIND_GUST',
           'VISIBILITY',
           'TEMPERATURE',
           'REL_HUMIDITY',
           'LOWEST_CLOUD_LAYER']


def preprocess_data(dataframe):
    # Filter rows
    dataframe = dataframe[dataframe[columns].notna().all(axis=1)]
    dataframe = dataframe[dataframe['CANCELLED'] == 0]
    dataframe = dataframe[dataframe['OP_UNIQUE_CARRIER'] == 'AA']
    dataframe = dataframe[dataframe['ORIGIN'] == 'JFK']

    # Exclude columns
    dataframe = dataframe.loc[:, columns]
    return dataframe


def split_datetime(dataframe):
    # Split data into two separate columns : day of the year and time
    datetime = pd.to_datetime(df["DEP_TIME"])
    dataframe["DEPARTURE_DAY"], dataframe["DEPARTURE_TIME"] = datetime.dt.date, datetime.dt.time

    # Get rid of previous column that contained date and time
    dataframe = dataframe.drop(columns=["DEP_TIME"])

    # Insert new columns at the beginning of the table
    dataframe = dataframe[["DEPARTURE_DAY", "DEPARTURE_TIME"] + dataframe.columns[:-2].to_list()]

    return dataframe


df = pd.read_csv(filepath_or_buffer='CompleteData.csv')
df = preprocess_data(dataframe=df)
df = split_datetime(dataframe=df)

df.to_csv("filtered_data.csv", index=False)

print(df.columns)
