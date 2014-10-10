# The Vertex class defines vertices in the Graph for CSAir's map
class Vertex
  # readers allow us to read, but not write to, metro and edges
  attr_reader :metro
  attr_reader :edges
  attr_accessor :previous
  attr_accessor :distance

  # constructor for a vertex
  # Each vertex is defined by a metro and a list of edges, each
  # of which determine the cities one can reach from this vertex
  # sets distance to max distance and previous to nil for Dijkstra
  # @param metro city located at this vertex
  def initialize(metro)
    @metro = metro
    # set up an empty adjacency list for this vertex's outgoing edges
    @edges = []
    @previous = nil
    @distance = (2**(0.size * 8 -2) -1)
  end

  private
  # method which creates and returns an array containing the distances
  # of each of this vertex's outgoing edges
  # @return array of distances to all adjacent vertices
  def distances
    @edges.collect{|edge| edge.distance}
  end

  public
  # method which allows us to add edges to the adjacency list
  # @param destination edge containing a vertex and distance
  def add_destination(destination)
    @edges << destination
  end

  # returns an array of all routes to a particular metro, given by its code
  # usually, only 1 or 0 elements in the array, depending on
  # whether or not the route exists
  # @param code Metro code
  # @return array containing an edge to the Metro, or 0 edges
  def find_route(code)
    @edges.select do |edge|
      edge.vertex.metro.code == code.to_s
    end
  end

  # method which determines the longest outgoing edge from
  # this vertex (i.e. the flight from this city with the greatest
  # distance)
  # @return edge corresponding to the longest flight from this vertex
  def longest_flight
    # return min integer if no edges
    return Edge.new('', -(2**(0.size * 8 -2))) if @edges.length == 0

    # find the largest distance in the array of edges
    max = distances.max
    @edges.each{|edge| return edge if edge.distance == max}
  end

  # method which determines the shortest outgoing edge from
  # this vertex (i.e. the flight from this city with the shortest
  # distance)
  # @return edge corresponding to the shortest flight from this vertex
  def shortest_flight
    # return max integer of no edges
    return Edge.new('', (2**(0.size * 8 -2) -1)) if @edges.length == 0

    # find the shortest distance in the distances array
    min = distances.min
    @edges.each{|edge| return edge if edge.distance == min}
  end

  # returns the length of a layover
  # the layover time is a function of the number of connections
  # @return float representing layover time in hours
  def layover
    # 0.16667 hrs = 10 min
    2.166666667 - (0.166666667 * edges.length)
  end
end