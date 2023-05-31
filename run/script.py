import pandas as pd


# Columns from original dataset that will be included
columns = ['DEP_TIME',
           'DEP_DELAY',
           'AIR_TIME',
           'DISTANCE',
           'YEAR OF MANUFACTURE',
           'MANUFACTURER',
           'WIND_DIR',
           'WIND_SPD',
           'WIND_GUST',
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
    datetime = pd.to_datetime(dataframe["DEP_TIME"])
    dataframe["DEPARTURE_DAY_OF_YEAR"], dataframe["DEPARTURE_DAYTIME"] = datetime.dt.dayofyear, datetime.dt.time

    # Get rid of previous column that contained date and time
    dataframe = dataframe.drop(columns=["DEP_TIME"])

    # Insert new columns at the beginning of the table
    dataframe = dataframe[["DEPARTURE_DAY_OF_YEAR", "DEPARTURE_DAYTIME"] + dataframe.columns[:-2].to_list()]

    return dataframe


def load_original():
    dataframe = pd.read_csv(filepath_or_buffer='CompleteData.csv')
    dataframe = preprocess_data(dataframe=dataframe)
    dataframe = split_datetime(dataframe=dataframe)
    return dataframe


def load_filtered():
    return pd.read_csv(filepath_or_buffer='filtered_data.csv')


def change_units(dataframe):
    dataframe["TEMPERATURE"] = round(dataframe["TEMPERATURE"] * (9/5) + 32)
    return dataframe


# df = load_filtered()
df = load_original()
df = change_units(df)

df.to_csv("filtered_data.csv", index=False)

