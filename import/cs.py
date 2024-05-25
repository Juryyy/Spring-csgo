#create a function, that takes .csv file and returns a number of columns and rows
import csv
def csv_reader(file_obj):
    reader = csv.reader(file_obj)
    data = list(reader)
    row_count = len(data)
    col_count = len(data[0])
    print('Rows: ', row_count, 'Cols: ', col_count)
    return row_count, col_count


import sys

if __name__ == "__main__":
    print("Started")
    import os

    filename = sys.argv[1]  # Get filename from command line arguments
    if filename.endswith('.csv'):
        with open(filename, 'r') as file_obj:
            print(filename)
            csv_reader(file_obj)