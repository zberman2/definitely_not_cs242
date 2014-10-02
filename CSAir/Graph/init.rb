# Init contains the set_up_map method, which we use in io.rb
# to create a Map with CSAir's data, located in map_data.json
module Init
  # method which parses the map_data.json file and uses it to
  # initialize the metros, vertices, edges, and ultimate the graph itself
  def set_up_map(file)
    # rubygems and json used for parsing map_data.json
    require 'rubygems'
    require 'json'

    # the following classes are required so that we can construct
    # these objects in this file
    require_relative 'edge'
    require_relative 'graph'
    require_relative 'metro'
    require_relative 'vertex'

    json = File.read(file)
    parsed = JSON.parse(json)

    # initialize an empty Hash table of vertices, indexed by their codes
    vertices = Hash.new

    parsed['metros'].each do |metro|
      # code will be a symbol used as a key in the vertices hash
      code = metro['code'].to_sym

      # create a vertex containing metro
      vertices[code] = Vertex.new(Metro.new(metro))
    end

    parsed['routes'].each do |route|
      ports = route['ports'] # array with 2 metro codes
      distance = route['distance']
      code_1 = ports[0].to_sym
      code_2 = ports[1].to_sym

      # create directed edges between port 1 and port 2
      to_port_1 = Edge.new(vertices[code_1], distance)
      to_port_2 = Edge.new(vertices[code_2], distance)

      # add edges to the vertices corresponding to code_1 and code_2
      vertices[code_1].add_destination(to_port_2)
      vertices[code_2].add_destination(to_port_1)
    end

    # lastly, create and return a graph defined by the set of
    # vertices defined in the vertices hash
    Graph.new(vertices)
  end
end