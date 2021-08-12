#!/usr/bin/env python3

import matplotlib.pyplot as plt
import pandas as pd

def plot_points( centers_filename: str, radius_filename: str, map_data_filename: str ):
    centers_df = pd.read_csv( centers_filename )
    radius_df = pd.read_csv( radius_filename )
    circles_df = centers_df.join(radius_df, on='id', lsuffix='_c', rsuffix='_r')
    map_data_df = pd.read_csv( map_data_filename )

    plt.title("Cell Index Method")
    plt.xlabel('Position X')
    plt.ylabel('Position Y')

    fig = plt.gcf()
    axes = fig.gca()
    
    for i, row in circles_df.iterrows():
        axes.add_artist(
            plt.Circle(
            ( float(row['x']), float(row['y'] )), 
            float(row['r']))
            )

    axes.set_xlim(0, map_data_df.iloc[0, 1])
    axes.set_ylim(0, map_data_df.iloc[0, 1])
    plt.show()


plot_points( 
    "output_particles_dynamic.csv",
    "output_particles_static.csv",
    "output_static.csv"
     )
