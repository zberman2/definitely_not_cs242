# Init contains the set_up_map method, which we use in io.rb
# to create a Map with CSAir's data, located in map_data.json
module Init
  # rubygems and json used for parsing map_data.json
  require 'rubygems'
  require 'json'

  # the following classes are required so that we can construct
  # these objects in this file
  require_relative 'edge'
  require_relative 'graph'
  require_relative 'metro'
  require_relative 'vertex'

  private

  # Private method which allows us to take a parsed JSON file
  # and return a Hash of metro code symbols -> vertices
  # which will make up the vertices in the graph
  # @param parsed Parsed JSON file
  # @return symbol: Vertex hash
  def set_up_vertices(parsed)
    vertices = Hash.new

    parsed['metros'].each do |metro|
      # code will be a symbol used as a key in the vertices hash
      code = metro['code'].to_sym

      # create a vertex containing metro
      vertices[code] = Vertex.new(Metro.new(metro))
    end
    vertices
  end

  # Helper method used specifically on making a map out of the
  # map_data.json file since it's routes are undirected and our
  # representation of the edges is directed
  # @param parsed Parsed JSON file
  # @param vertices symbol: Vertex hash
  def set_up_edges_undirected(parsed, vertices)
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
  end

  # Helper method used to set up the directed edges in our graph
  # @param parsed Parsed JSON file
  # @vertices symbol: Vertex hash
  def set_up_edges_directed(parsed, vertices)
    parsed['routes'].each do |route|
      ports = route['ports'] # array with 2 metro codes
      distance = route['distance']
      code_1 = ports[0].to_sym
      code_2 = ports[1].to_sym

      # create directed edge from port 1 to port 2
      to_port_2 = Edge.new(vertices[code_2], distance)

      # add edge to the vertex corresponding to code_1
      vertices[code_1].add_destination(to_port_2)
    end
  end

  # Creates and returns a hash which represents a metro
  # @param vertex Vertex containing the metro information
  # @return metro_hash Hash containing metro info
  def metro_hash(vertex)
    metro_hash = Hash.new
    metro = vertex.metro
    metro_hash['code'] = metro.code
    metro_hash['name'] = metro.name
    metro_hash['country'] = metro.country
    metro_hash['continent'] = metro.continent
    metro_hash['timezone'] = metro.timezone
    metro_hash['coordinates'] = metro.coordinates
    metro_hash['population'] = metro.population
    metro_hash['region'] = metro.region
    metro_hash
  end

  # Creates and return sa hash which represents a route with the
  # starting and ending metros and distance
  # @param edge Edge containing the route
  # @param code Starting metro code
  # @return route_hash Hash containing route info
  def route_hash(edge, code)
    route_hash = Hash.new
    ports = Array.new
    # add starting location, then the destination
    ports << code
    ports << edge.vertex.metro.code
    route_hash['ports'] = ports
    route_hash['distance'] = edge.distance
    route_hash
  end

  # Creates and returns a hash which will contain all information
  # stored in a json file
  # @param metros metros Hash
  # @param routes routes Hash
  # @return json_hash Hash which will be written to JSON format
  def json_hash(metros, routes)
    json_hash = Hash.new
    json_hash['metros'] = metros
    json_hash['routes'] = routes
    json_hash
  end

  public

  # Method used to turn the map_data.json format with its undirected edges
  # into a JSON file with directed edges.
  # @param file File to write to with the new JSON
  def formalize_map_data(file)
    json = File.read('JSON/map_data.json')
    parsed = JSON.parse(json)
    json_hub = File.read('JSON/cmi_hub.json')
    parsed_hub = JSON.parse(json_hub)

    # set up the vertices, then place routes inside them
    vertices = set_up_vertices(parsed)
    vertices.merge!(set_up_vertices(parsed_hub))
    set_up_edges_undirected(parsed, vertices)
    set_up_edges_undirected(parsed_hub, vertices)

    # create a graph defined by the set of
    # vertices defined in the vertices hash
    map = Graph.new(vertices)

    # place our new Hash with directed edges into the new json file
    write_to_file(map, file)
  end

  # method which parses the json file and uses it to
  # initialize the metros, vertices, edges, and ultimate the graph itself
  # @param file JSON file containing map info
  def set_up_map(file)
    json = File.read(file)
    parsed = JSON.parse(json)

    # set up the vertices and then place directed edges in them
    vertices = set_up_vertices(parsed)
    set_up_edges_directed(parsed, vertices)

    # lastly, create and return a graph defined by the set of
    # vertices defined in the vertices hash
    Graph.new(vertices)
  end

  # Method used for taking a graph and creating a JSON which
  # represents its information
  # @param graph Map representation
  # @param file File which will contain JSON of the graph
  def write_to_file(graph, file)
    metros = Array.new
    routes = Array.new

    # metros will contain metro hashes
    # routes will contain route hashes
    graph.vertices.each do |_, vertex|
      metros << metro_hash(vertex)
      vertex.edges.each do |edge|
        routes << route_hash(edge, vertex.metro.code)
      end
    end

    File.open(file, 'w') do |json_file|
      json_file.write(JSON.pretty_generate(json_hash(metros, routes)))
    end
  end
end