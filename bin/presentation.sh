#
# Arguments
#
#    staticFile={input.txt}
#    dynamicFile={input.txt}
#    wallPeriod={true|false}
#    randomize={true|false}
#    sameRadius={true|false}
#

#
# Outputs
#
#    lista de vecinos
#    tiempo de ejecucion
#    figura que muestre las posiciones de todas las particulas y que identifique una de ellas y sus vecinos
#

java -jar cellindexmethod-0.0.1-SNAPSHOT.jar staticFile=input/static.txt dynamicFile=input/dynamic.txt wallPeriod=false randomize=false sameRadius=false 2> output_exec_time.txt 1> /dev/null

# Lista de vecinos con Cell Index Method
cat output_neighbors_cmi.csv
echo ""

# Tiempos de Ejecucion
cat output_exec_time.txt
echo ""

# Figura con las posiciones de las particulas
python plotter.py 1
