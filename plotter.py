#!/usr/bin/env python3

import matplotlib.pyplot as plt
from numpy.core.numeric import NaN
import pandas as pd
import numpy as np
import sys
import math

def plot_points( centers_filename: str, radius_filename: str, map_data_filename: str,  neighbors_filename: str, study_particle_id  ):
    centers_df = pd.read_csv( centers_filename )
    radius_df = pd.read_csv( radius_filename )
    circles_df = centers_df.join(radius_df, on='id', lsuffix='_c', rsuffix='_r')
    map_data_df = pd.read_csv( map_data_filename )
    neighbors_df = pd.read_csv( neighbors_filename )

    plt.title("Cell Index Method")
    plt.xlabel('Position X')
    plt.ylabel('Position Y')

    fig = plt.gcf()
    axes = fig.gca()

    
    for i, row in circles_df.iterrows():
        if int(row['id_c']) == study_particle_id:
            color = 'blue'
        else:
            is_neighbor = False
            for j, rowN in neighbors_df.iterrows():
                if not math.isnan(rowN['p']):
                    neighbors = str(rowN['p']).split()
                    for neighbor in neighbors:
                        if int(row['id_c']) == int(float(neighbor)):
                            is_neighbor = True
            if is_neighbor:
                color = 'green'
            else:
                color = 'red'
        axes.add_artist(
            plt.Circle(
                ( row['x'], row['y']), 
                row['r'],
                color = color
                ),
            )
    major_ticks = np.arange(0, map_data_df.iloc[0, 1], map_data_df.iloc[0, 2])
    axes.set_xticks(major_ticks)
    axes.set_yticks(major_ticks)

    axes.set_xlim(0, map_data_df.iloc[0, 1])
    axes.set_ylim(0, map_data_df.iloc[0, 1])
    plt.grid()
    plt.show()

if len(sys.argv) != 2:
    print('Error')
else:
    plot_points( 
        "output_particles_dynamic.csv",
        "output_particles_static.csv",
        "output_static.csv",
        "output_neighbors_cmi.csv",
        int(sys.argv[1])
    )
