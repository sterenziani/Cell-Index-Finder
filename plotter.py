#!/usr/bin/env python3

import matplotlib.pyplot as plt
from numpy.core.numeric import NaN
import pandas as pd
import numpy as np
import sys

from pandas.core.frame import DataFrame

def create_neighbor_dict( neighbors_df: DataFrame ):
    neighbor_dict = dict()
    for i, row in neighbors_df.iterrows():
        id = int(row['id'])
        neighbor_dict[id] = set()
        if len(row['p']) > 0 and row['p'] != 'nan':
            neighbors = row['p'].split()
            for neighbor in neighbors:
                neighbor_dict[id].add(int(neighbor))
    return neighbor_dict


def plot_points( centers_filename: str, radius_filename: str, map_data_filename: str,  neighbors_filename: str, study_particle_id  ):
    centers_df = pd.read_csv( centers_filename )
    radius_df = pd.read_csv( radius_filename )
    circles_df = centers_df.join(radius_df, on='id', lsuffix='_c', rsuffix='_r')
    map_data_df = pd.read_csv( map_data_filename )
    neighbors_df = pd.read_csv( neighbors_filename )
    neighbors_df['p'] = neighbors_df['p'].astype(str)
    neighbors_dict = create_neighbor_dict( neighbors_df)
    neighbors_set = neighbors_dict[study_particle_id]

    plt.title("Cell Index Method")
    plt.xlabel('Position X')
    plt.ylabel('Position Y')

    fig = plt.gcf()
    axes = fig.gca()

    for i, row in circles_df.iterrows():
        if int(row['id_c']) == study_particle_id:
            color = 'blue'
            axes.add_artist(
            plt.Circle(
                ( row['x'], row['y']), 
                map_data_df.iloc[0, 3],
                color = 'purple',
                fill=False
                ),
            )
        else:
            if int(row['id_c']) in neighbors_set:
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
